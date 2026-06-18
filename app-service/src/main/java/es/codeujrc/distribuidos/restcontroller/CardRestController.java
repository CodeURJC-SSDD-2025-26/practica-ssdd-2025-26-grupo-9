package es.codeujrc.distribuidos.restcontroller;

import es.codeujrc.distribuidos.DTOs.CardBasicResponseDTO;
import es.codeujrc.distribuidos.DTOs.CardDetailResponseDTO;
import es.codeujrc.distribuidos.DTOs.CardRequestDTO;
import es.codeujrc.distribuidos.service.CardService;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/api/v1/cards")
public class CardRestController {

    @Autowired
    private CardService cardService;

    @GetMapping
    public ResponseEntity<Page<CardBasicResponseDTO>> getCards(Pageable pageable) {
        return ResponseEntity.ok(cardService.findAll(pageable));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCard(@PathVariable Long cardId) {
        Optional<CardDetailResponseDTO> card = cardService.findDetailById(cardId);

        if (card.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Card not found"));
        }

        return ResponseEntity.ok(card.get());
    }

    @GetMapping("/{cardId}/image")
    public ResponseEntity<?> getCardImage(@PathVariable Long cardId) {
        Optional<byte[]> image = cardService.getImage(cardId);

        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Card image not found"));
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(image.get());
    }

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody CardRequestDTO cardRequestDTO) {
        try {
            CardDetailResponseDTO responseDTO = cardService.create(cardRequestDTO);

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

    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateCard(
            @PathVariable Long cardId,
            @RequestBody CardRequestDTO cardRequestDTO) {
        try {
            Optional<CardDetailResponseDTO> updatedCard = cardService.update(cardId, cardRequestDTO);

            if (updatedCard.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Card not found"));
            }

            return ResponseEntity.ok(updatedCard.get());

        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", exception.getMessage()));
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId) {
        if (!cardService.deleteIfExists(cardId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Card not found"));
        }

        return ResponseEntity.noContent().build();
    }
}