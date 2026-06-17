package es.codeujrc.distribuidos.restcontroller;

import es.codeujrc.distribuidos.DTOs.CardBasicResponseDTO;
import es.codeujrc.distribuidos.DTOs.CardDetailResponseDTO;
import es.codeujrc.distribuidos.DTOs.CardRequestDTO;
import es.codeujrc.distribuidos.DTOMappers.CardMapper;
import es.codeujrc.distribuidos.entity.Card;
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

    @Autowired
    private CardMapper cardMapper;

    @GetMapping
    public ResponseEntity<Page<CardBasicResponseDTO>> getCards(Pageable pageable) {
        Page<Card> cardsPage = cardService.findAll(pageable);
        return ResponseEntity.ok(cardsPage.map(cardMapper::toBasicDTO));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCard(@PathVariable Long cardId) {
        Optional<Card> card = cardService.findById(cardId);

        if (card.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Card not found"));
        }

        return ResponseEntity.ok(cardMapper.toDetailDTO(card.get()));
    }

    @GetMapping("/{cardId}/image")
    public ResponseEntity<?> getCardImage(@PathVariable Long cardId) {
        Optional<Card> card = cardService.findById(cardId);

        if (card.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Card not found"));
        }

        byte[] image = card.get().getImage();
        if (image == null || image.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Card image not found"));
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(image);
    }

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody CardRequestDTO cardRequestDTO) {
        if (cardRequestDTO.name() == null || cardRequestDTO.name().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Card name is required"));
        }

        Card card = cardMapper.toDomain(cardRequestDTO);
        cardService.save(card);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(card.getId())
                .toUri();

        CardDetailResponseDTO responseDTO = cardMapper.toDetailDTO(card);
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateCard(
            @PathVariable Long cardId,
            @RequestBody CardRequestDTO cardRequestDTO) {

        Optional<Card> savedCard = cardService.findById(cardId);

        if (savedCard.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Card not found"));
        }

        if (cardRequestDTO.name() == null || cardRequestDTO.name().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Card name is required"));
        }

        Card card = savedCard.get();
        card.setName(cardRequestDTO.name());
        card.setDescription(cardRequestDTO.description());
        card.setTriggerEffect(cardRequestDTO.triggerEffect());
        card.setCrew(cardRequestDTO.crew());
        card.setCost(cardRequestDTO.cost());
        card.setPower(cardRequestDTO.power());
        card.setHealth(cardRequestDTO.health());
        card.setType(cardRequestDTO.type());
        card.setAttribute(cardRequestDTO.attribute());
        card.setColor(cardRequestDTO.color());
        card.setCounter(cardRequestDTO.counter());

        cardService.save(card);

        return ResponseEntity.ok(cardMapper.toDetailDTO(card));
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId) {
        if (!cardService.exist(cardId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Card not found"));
        }

        cardService.delete(cardId);
        return ResponseEntity.noContent().build();
    }
}