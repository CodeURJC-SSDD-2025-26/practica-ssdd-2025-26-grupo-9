package es.codeujrc.distribuidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeujrc.distribuidos.entity.Commentary;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
    
}
