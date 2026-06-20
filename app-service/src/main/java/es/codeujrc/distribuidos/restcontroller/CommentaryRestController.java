package es.codeujrc.distribuidos.restcontroller;

import es.codeujrc.distribuidos.DTOs.CommentaryRequestDTO;
import es.codeujrc.distribuidos.DTOs.CommentaryResponseDTO;
import es.codeujrc.distribuidos.DTOMappers.CommentaryMapper;
import es.codeujrc.distribuidos.entity.Commentary;
import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.service.CommentaryService;
import es.codeujrc.distribuidos.service.DeckService;
import es.codeujrc.distribuidos.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/decks/{deckId}/commentaries")
public class CommentaryRestController {

    @Autowired
    private CommentaryService commentaryService;

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentaryMapper commentaryMapper;

    @GetMapping
    public ResponseEntity<Page<CommentaryResponseDTO>> getCommentaries(
            @PathVariable Long deckId,
            Pageable pageable) {
        
        if (!deckService.exist(deckId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Page<Commentary> commentariesPage = commentaryService.findByDeckIdPaginated(deckId, pageable);
        
        Page<CommentaryResponseDTO> dtoPage = commentariesPage.map(commentaryMapper::toResponseDTO);

        return ResponseEntity.ok(dtoPage);
    }

    @PostMapping
    public ResponseEntity<CommentaryResponseDTO> createCommentary(
            @PathVariable Long deckId,
            @RequestBody CommentaryRequestDTO requestDTO) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentUsername == null || currentUsername.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!deckService.exist(deckId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Deck deck = deckService.findById(deckId);
        User currentUser = userService.findByUsername(currentUsername);

        Commentary newComment = new Commentary();
        newComment.setContent(requestDTO.content());
        newComment.setDeck(deck);
        newComment.setUser(currentUser);

        commentaryService.save(newComment);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentaryMapper.toResponseDTO(newComment));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentaryResponseDTO> deleteCommentary(
            @PathVariable Long deckId,
            @PathVariable Long commentId) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentUsername == null || currentUsername.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Commentary comment = commentaryService.findById(commentId).orElse(null);
        
        if (comment == null || !comment.getDeck().getId().equals(deckId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User currentUser = userService.findByUsername(currentUsername);

        boolean isOwner = comment.getUser().getUsername().equals(currentUsername);
        boolean isAdmin = currentUser.getRole().equals(User.Role.ADMIN);

        if (!isOwner && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        CommentaryResponseDTO responseDTO = commentaryMapper.toResponseDTO(comment);
        commentaryService.delete(commentId);
        
        return ResponseEntity.ok(responseDTO);
    }
}