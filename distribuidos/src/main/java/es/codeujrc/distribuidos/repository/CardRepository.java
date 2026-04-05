package es.codeujrc.distribuidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeujrc.distribuidos.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    
}
