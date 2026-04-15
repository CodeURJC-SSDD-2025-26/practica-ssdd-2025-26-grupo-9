package es.codeujrc.distribuidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import es.codeujrc.distribuidos.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c.name, SIZE(c.decks) FROM Card c WHERE SIZE(c.decks) > 0 ORDER BY SIZE(c.decks) DESC")
    List<Object[]> countCardUsageInDecks();
}
