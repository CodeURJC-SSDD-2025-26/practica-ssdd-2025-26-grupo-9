package es.codeujrc.distribuidos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.User;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findByUserId(Long userId);

    @Query("SELECT d FROM Deck d WHERE d.user IN ?1")
    List<Deck> findByFollowing(List<User> following);
}
