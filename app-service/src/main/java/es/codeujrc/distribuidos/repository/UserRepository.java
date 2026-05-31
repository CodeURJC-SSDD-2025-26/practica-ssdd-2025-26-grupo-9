package es.codeujrc.distribuidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeujrc.distribuidos.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
    
    Optional<User> findById(long id);
}
