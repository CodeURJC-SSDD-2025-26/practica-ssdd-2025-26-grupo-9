package es.codeujrc.distribuidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}