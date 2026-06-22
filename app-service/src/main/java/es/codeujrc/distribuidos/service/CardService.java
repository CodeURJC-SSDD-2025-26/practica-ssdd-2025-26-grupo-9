package es.codeujrc.distribuidos.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.codeujrc.distribuidos.DTOMappers.CardChartMapper;
import es.codeujrc.distribuidos.DTOMappers.CardMapper;
import es.codeujrc.distribuidos.DTOs.CardBasicResponseDTO;
import es.codeujrc.distribuidos.DTOs.CardChartDTO;
import es.codeujrc.distribuidos.DTOs.CardDetailResponseDTO;
import es.codeujrc.distribuidos.DTOs.CardRequestDTO;
import es.codeujrc.distribuidos.entity.Card;
import es.codeujrc.distribuidos.repository.CardRepository;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private CardChartMapper cardChartMapper;

    public Optional<Card> findById(long id) {
        return repository.findById(id);
    }

    public boolean exist(long id) {
        return repository.existsById(id);
    }

    public List<Card> findAll() {
        return repository.findAll();
    }

    public Page<CardBasicResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(cardMapper::toBasicDTO);
    }

    public Optional<CardDetailResponseDTO> findDetailById(long id) {
        return repository.findById(id).map(cardMapper::toDetailDTO);
    }

    public void save(Card card) {
        repository.save(card);
    }

    public CardDetailResponseDTO create(CardRequestDTO cardRequestDTO) {
        validateCardRequest(cardRequestDTO);

        Card card = cardMapper.toDomain(cardRequestDTO);
        updateCardImage(card, cardRequestDTO);
        repository.save(card);

        return cardMapper.toDetailDTO(card);
    }

    public Optional<CardDetailResponseDTO> update(long id, CardRequestDTO cardRequestDTO) {
        validateCardRequest(cardRequestDTO);

        Optional<Card> savedCard = repository.findById(id);
        if (savedCard.isEmpty()) {
            return Optional.empty();
        }

        Card card = savedCard.get();
        updateCardFields(card, cardRequestDTO);
        updateCardImage(card, cardRequestDTO);
        repository.save(card);

        return Optional.of(cardMapper.toDetailDTO(card));
    }

    public boolean deleteIfExists(long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public void saveCard(Card card, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            card.setImage(imageFile.getBytes());
        } else {
            if (card.getId() != null) {
                Optional<Card> existingCard = repository.findById(card.getId());
                if (existingCard.isPresent()) {
                    card.setImage(existingCard.get().getImage());
                }
            }
        }
        repository.save(card);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public byte[] getCardImage(long id) {
        Optional<Card> card = repository.findById(id);
        if (card.isPresent()) {
            return card.get().getImage();
        }
        return null;
    }

    public Optional<byte[]> getImage(long id) {
        return repository.findById(id)
                .map(Card::getImage)
                .filter(image -> image.length > 0);
    }

    public Map<String, Object> getMetaCardsData() {

        List<Object[]> results = repository.countCardUsageInDecks();

        List<String> names = new ArrayList<>();
        List<Integer> deckCounts = new ArrayList<>();

        results.stream().limit(7).forEach(row -> {
            names.add((String) row[0]);
            deckCounts.add((Integer) row[1]);
        });

        Map<String, Object> map = new HashMap<>();
        map.put("names", names);
        map.put("counts", deckCounts);
        return map;
    }

    public CardChartDTO getMetaCardsChartData() {
        Map<String, Object> chartData = getMetaCardsData();
        return cardChartMapper.toDTO(chartData);
    }

    private void validateCardRequest(CardRequestDTO cardRequestDTO) {
        if (cardRequestDTO.name() == null || cardRequestDTO.name().isBlank()) {
            throw new IllegalArgumentException("Card name is required");
        }
    }

    private void updateCardFields(Card card, CardRequestDTO cardRequestDTO) {
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
    }

    private void updateCardImage(Card card, CardRequestDTO cardRequestDTO) {
        if (cardRequestDTO.imageBase64() == null || cardRequestDTO.imageBase64().isBlank()) {
            return;
        }

        String cleanImage = cardRequestDTO.imageBase64();
        if (cleanImage.contains(",")) {
            cleanImage = cleanImage.substring(cleanImage.indexOf(',') + 1);
        }

        try {
            card.setImage(Base64.getDecoder().decode(cleanImage));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Card image must be valid Base64");
        }
    }
}