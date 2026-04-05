package es.codeujrc.distribuidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeujrc.distribuidos.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
