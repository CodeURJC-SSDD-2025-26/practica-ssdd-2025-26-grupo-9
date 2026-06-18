package es.codeujrc.distribuidos.restcontroller;

import es.codeujrc.distribuidos.DTOs.DeckBasicResponseDTO;
import es.codeujrc.distribuidos.DTOs.DeckDetailResponseDTO;
import es.codeujrc.distribuidos.DTOs.DeckRequestDTO;
import es.codeujrc.distribuidos.service.DeckService;

import java.net.URI;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/decks")
public class DeckRestController {

    @Autowired
    private DeckService deckService;

    @GetMapping
    public ResponseEntity<Page<DeckBasicResponseDTO>> getDecks(Pageable pageable) {
        return ResponseEntity.ok(deckService.findAll(pageable));
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<?> getDeck(@PathVariable Long deckId) {
        Optional<DeckDetailResponseDTO> deck = deckService.findDetailById(deckId);

        if (deck.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Deck not found"));
        }

        return ResponseEntity.ok(deck.get());
    }

    @PostMapping
    public ResponseEntity<?> createDeck(
            @RequestBody DeckRequestDTO deckRequestDTO,
            Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication is required"));
        }

        try {
            DeckDetailResponseDTO responseDTO = deckService.create(deckRequestDTO, principal.getName());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(responseDTO.id())
                    .toUri();

            return ResponseEntity.created(location).body(responseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", exception.getMessage()));
        }
    }

    @PutMapping("/{deckId}")
    public ResponseEntity<?> updateDeck(
            @PathVariable Long deckId,
            @RequestBody DeckRequestDTO deckRequestDTO,
            Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication is required"));
        }

        try {
            Optional<DeckDetailResponseDTO> updatedDeck = deckService.update(deckId, deckRequestDTO,
                    principal.getName());

            if (updatedDeck.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Deck not found"));
            }

            return ResponseEntity.ok(updatedDeck.get());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", exception.getMessage()));
        } catch (SecurityException exception) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", exception.getMessage()));
        }
    }

    @DeleteMapping("/{deckId}")
    public ResponseEntity<?> deleteDeck(
            @PathVariable Long deckId,
            Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication is required"));
        }

        try {
            if (!deckService.deleteIfExistsAndAllowed(deckId, principal.getName())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Deck not found"));
            }

            return ResponseEntity.noContent().build();
        } catch (SecurityException exception) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", exception.getMessage()));
        }
    }
}