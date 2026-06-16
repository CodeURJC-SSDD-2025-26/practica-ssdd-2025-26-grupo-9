package es.codeujrc.distribuidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeujrc.distribuidos.entity.Commentary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
    Page<Commentary> findByDeckId(Long deckId, Pageable pageable);
}
