package es.codeujrc.distribuidos.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.repository.UserRepository;
import es.codeujrc.distribuidos.security.SecurityContextUpdater;

@Service
public class UserService {

	@Autowired
	private UserRepository usersRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SecurityContextUpdater securityContextManager;

	private byte[] loadImage(String path) throws IOException {

        Resource imageRes = new ClassPathResource(path);
        if (imageRes.exists()) {
            return imageRes.getContentAsByteArray();
        }
        
        String fsPath = "src/main/resources/" + path;
        Resource fsResource = new FileSystemResource(fsPath);
        if (fsResource.exists()) {
            return fsResource.getContentAsByteArray();
        }
        
        return null; 
    }

	public User findById(long id) {
		return usersRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found: " + id));
	}

	public User findByUsername(String username) {
		return usersRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found: " + username));
	}

	public boolean exist(long id) {
		return usersRepository.existsById(id);
	}

	public List<User> findAll() {
		return usersRepository.findAll();
	}

	public void save(User user) {
		usersRepository.save(user);
	}

	public void delete(long id) {
		usersRepository.deleteById(id);
	}

	public boolean registerNewUser(User user) throws IOException {
		if (usersRepository.existsByEmail(user.getEmail()) || usersRepository.existsByUsername(user.getUsername())) {
			return false;
		}
		user.setRole(User.Role.REGISTERED);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		byte[] defaultImg = loadImage("profileimages/ZaZaNoMi.png");
		user.setImage(defaultImg);
		usersRepository.save(user);
		return true;
	}

	public byte[] getUserImage(long id) {
		User user = this.findById(id);
		return user.getImage();
	}

	private Pair<Boolean, Boolean> updateCommon(User user, String newUsername, String newEmail, String newPassword,
			MultipartFile imageFile) throws IOException {
		boolean userConflict = false;
		boolean emailConflict = false;
		if (newUsername != null && !newUsername.isBlank() && !newUsername.equals(user.getUsername())) {
			if (usersRepository.existsByUsername(newUsername)) {
				userConflict = true;
			} else {
				user.setUsername(newUsername);
			}
		}
		if (newEmail != null && !newEmail.isBlank() && !newEmail.equals(user.getEmail())) {
			if (usersRepository.existsByEmail(newEmail)) {
				emailConflict = true;
			} else {
				user.setEmail(newEmail);
			}
		}
		if (newPassword != null && !newPassword.isBlank()) {
			user.setPassword(passwordEncoder.encode(newPassword));
		}
		if (imageFile != null && !imageFile.isEmpty()) {
			user.setImage(imageFile.getBytes());
		}
		return Pair.of(userConflict, emailConflict);
	}

	public Pair<Boolean, Boolean> updateUser(long id, String newUsername, String newEmail, String newPassword,
			MultipartFile imageFile)
			throws IOException {
		User user = this.findById(id);
		String oldUsername = user.getUsername();

		Pair<Boolean, Boolean> conflicts = updateCommon(user, newUsername, newEmail, newPassword, imageFile);

		if (!oldUsername.equals(user.getUsername()) && !conflicts.getFirst()) {
			securityContextManager.updateSession(user);
		}

		usersRepository.save(user);
		return conflicts;
	}

	public Pair<Boolean, Boolean> updateUserAsAdmin(long id, String newUsername, String newEmail, String newPassword,
			String newRole, MultipartFile imageFile) throws IOException {
		User user = this.findById(id);
		Pair<Boolean, Boolean> conflicts = updateCommon(user, newUsername, newEmail, newPassword, imageFile);

		if (newRole != null && !newRole.isBlank()) {
			try {
				user.setRole(User.Role.valueOf(newRole));
			} catch (IllegalArgumentException e) {
			}
		}
		usersRepository.save(user);
		return conflicts;
	}

	public void follow(Long currentUserId, Long targetUserId) {
		User currentUser = usersRepository.findById(currentUserId).orElseThrow();
		User targetUser = usersRepository.findById(targetUserId).orElseThrow();

		if (!currentUser.getFollowing().contains(targetUser)) {
			currentUser.getFollowing().add(targetUser);
			usersRepository.save(currentUser);
		}
	}

	public void unfollow(Long currentUserId, Long targetUserId) {
		User currentUser = usersRepository.findById(currentUserId).orElseThrow();
		User targetUser = usersRepository.findById(targetUserId).orElseThrow();

		if (currentUser.getFollowing().contains(targetUser)) {
			currentUser.getFollowing().remove(targetUser);
			usersRepository.save(currentUser);
		}
	}

}