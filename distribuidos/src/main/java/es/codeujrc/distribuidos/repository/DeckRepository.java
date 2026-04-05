package es.codeujrc.distribuidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeujrc.distribuidos.entity.Deck;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    
}
