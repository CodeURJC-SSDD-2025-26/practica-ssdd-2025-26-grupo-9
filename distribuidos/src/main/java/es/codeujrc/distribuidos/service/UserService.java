package es.codeujrc.distribuidos.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository usersRepository;

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
	public boolean registerNewUser(User user){
		if(usersRepository.existsByEmail(user.getEmail())){
			return false;
		}
		user.setRole(User.Role.REGISTERED);
		usersRepository.save(user);
		return true;
	}

	public User login(String email, String password) {
    Optional<User> user = usersRepository.findByEmail(email);
    
    if (user.isPresent() && user.get().getPassword().equals(password)) {
        return user.get();
    }
    
    return null;
}
}