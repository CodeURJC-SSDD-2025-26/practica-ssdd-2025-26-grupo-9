package es.codeujrc.distribuidos.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository usersRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
		if (usersRepository.existsByEmail(user.getEmail())) {
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

	public void updateUser(long id, String newUsername, String newEmail, String newPassword, MultipartFile imageFile)
			throws IOException {
		User user = usersRepository.findById(id).orElseThrow();

		if (newUsername != null && !newUsername.isBlank()) {
			user.setUsername(newUsername);
		}
		if (newEmail != null && !newEmail.isBlank()) {
			user.setEmail(newEmail);
		}
		if (newPassword != null && !newPassword.isBlank()) {
			user.setPassword(passwordEncoder.encode(newPassword));
		}
		if (imageFile != null && !imageFile.isEmpty()) {
			user.setImage(imageFile.getBytes());
		}
		usersRepository.save(user);
	}
}