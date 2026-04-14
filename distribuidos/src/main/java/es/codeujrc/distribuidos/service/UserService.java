package es.codeujrc.distribuidos.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	public Optional<User> findById(long id) {
		return usersRepository.findById(id);
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

	public boolean registerNewUser(User user) {
		if (usersRepository.existsByEmail(user.getEmail()) || usersRepository.existsByUsername(user.getUsername())) {
			return false;
		}
		user.setRole(User.Role.REGISTERED);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
		return true;
	}

	public byte[] getUserImage(long id) {
		Optional<User> user = usersRepository.findById(id);
		if (user.isPresent()) {
			return user.get().getImage();
		}
		return null;
	}

	public Pair<Boolean, Boolean> updateUser(long id, String newUsername, String newEmail, String newPassword,
			MultipartFile imageFile)
			throws IOException {
		User user = usersRepository.findById(id).orElseThrow();
		boolean userConflict = false;
		boolean emailConflict = false;

		if (newUsername != null && !newUsername.isBlank() && !newUsername.equals(user.getUsername())) {
			if (usersRepository.existsByUsername(newUsername)) {
				userConflict = true;
			} else {
				user.setUsername(newUsername);
				securityContextManager.updateSession(user);
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
		usersRepository.save(user);

		return Pair.of(userConflict, emailConflict);
	}
}