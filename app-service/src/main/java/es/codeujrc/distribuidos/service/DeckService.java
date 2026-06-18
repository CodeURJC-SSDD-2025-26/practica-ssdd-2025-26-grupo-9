package es.codeujrc.distribuidos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import es.codeujrc.distribuidos.DTOMappers.DeckMapper;
import es.codeujrc.distribuidos.DTOs.DeckBasicResponseDTO;
import es.codeujrc.distribuidos.DTOs.DeckDetailResponseDTO;
import es.codeujrc.distribuidos.DTOs.DeckRequestDTO;
import es.codeujrc.distribuidos.entity.Card;
import es.codeujrc.distribuidos.entity.Commentary;
import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.repository.DeckRepository;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeckMapper deckMapper;

    public List<Deck> findByUserId(Long userId) {
        return deckRepository.findByUserId(userId);
    }

    public Deck findById(long id) {
        return deckRepository.findById(id).orElse(null);
    }

    public boolean exist(long id) {
        return deckRepository.existsById(id);
    }

    public List<Deck> findAll() {
        return deckRepository.findAll();
    }

    public Page<DeckBasicResponseDTO> findAll(Pageable pageable) {
        return deckRepository.findAll(pageable).map(deckMapper::toBasicDTO);
    }

    public Optional<DeckDetailResponseDTO> findDetailById(long id) {
        return deckRepository.findById(id).map(deckMapper::toDetailDTO);
    }

    public void save(Deck deck) {
        deckRepository.save(deck);
    }

    public void delete(long id) {
        deckRepository.deleteById(id);
    }

    public DeckDetailResponseDTO create(DeckRequestDTO deckRequestDTO, String username) {
        validateDeckRequest(deckRequestDTO);

        User user = userService.findByUsername(username);
        Deck deck = createDeckEntity(deckRequestDTO, user);
        deckRepository.save(deck);

        return deckMapper.toDetailDTO(deck);
    }

    public Optional<DeckDetailResponseDTO> update(long id, DeckRequestDTO deckRequestDTO, String username) {
        validateDeckName(deckRequestDTO);

        Optional<Deck> savedDeck = deckRepository.findById(id);
        if (savedDeck.isEmpty()) {
            return Optional.empty();
        }

        Deck deck = savedDeck.get();
        User currentUser = userService.findByUsername(username);
        validateDeckAccess(deck, currentUser);

        deck.setName(deckRequestDTO.name());
        deck.setDescription(deckRequestDTO.description());

        if (deckRequestDTO.cardIds() != null) {
            deck.setCards(findCards(deckRequestDTO.cardIds()));
        }

        deckRepository.save(deck);
        return Optional.of(deckMapper.toDetailDTO(deck));
    }

    public boolean deleteIfExistsAndAllowed(long id, String username) {
        Optional<Deck> savedDeck = deckRepository.findById(id);
        if (savedDeck.isEmpty()) {
            return false;
        }

        User currentUser = userService.findByUsername(username);
        validateDeckAccess(savedDeck.get(), currentUser);

        deckRepository.delete(savedDeck.get());
        return true;
    }

    public boolean createDeckWithCards(String name, String description, User user,
            String cardId1, String cardId2, String cardId3, String cardId4, String cardId5, String cardId6) {

        try {
            List<Long> cardIds = parseCardIds(cardId1, cardId2, cardId3, cardId4, cardId5, cardId6);
            DeckRequestDTO deckRequestDTO = new DeckRequestDTO(name, description, cardIds);
            Deck deck = createDeckEntity(deckRequestDTO, user);
            deckRepository.save(deck);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    public List<Deck> getDecksFromFollowing(User user) {
        List<User> following = user.getFollowing();
        if (following.isEmpty()) {
            return new ArrayList<>();
        }
        return deckRepository.findByFollowing(following);
    }

    public List<Pair<Deck, List<Pair<Commentary, Boolean>>>> getDecksWithCommentOwnership(String currentUsername) {

        List<Deck> decks = this.findAll();
        List<Pair<Deck, List<Pair<Commentary, Boolean>>>> decksForView = new ArrayList<>();

        for (Deck deck : decks) {
            List<Pair<Commentary, Boolean>> commentsList = new ArrayList<>();

            for (Commentary comment : deck.getCommentaries()) {
                boolean isOwner = currentUsername != null && comment.getUser().getUsername().equals(currentUsername);
                commentsList.add(Pair.of(comment, isOwner));
            }
            decksForView.add(Pair.of(deck, commentsList));
        }

        return decksForView;
    }

    private Deck createDeckEntity(DeckRequestDTO deckRequestDTO, User user) {
        List<Card> cards = findCards(deckRequestDTO.cardIds());
        return new Deck(deckRequestDTO.name(), deckRequestDTO.description(), cards, new ArrayList<>(), user);
    }

    private void validateDeckRequest(DeckRequestDTO deckRequestDTO) {
        validateDeckName(deckRequestDTO);

        if (deckRequestDTO.cardIds() == null || deckRequestDTO.cardIds().size() != 6) {
            throw new IllegalArgumentException("Deck must have exactly 6 cards");
        }
    }

    private void validateDeckName(DeckRequestDTO deckRequestDTO) {
        if (deckRequestDTO.name() == null || deckRequestDTO.name().isBlank()) {
            throw new IllegalArgumentException("Deck name is required");
        }
    }

    private List<Card> findCards(List<Long> cardIds) {
        if (cardIds == null || cardIds.size() != 6) {
            throw new IllegalArgumentException("Deck must have exactly 6 cards");
        }

        List<Card> cards = new ArrayList<>();
        for (Long cardId : cardIds) {
            if (cardId == null) {
                throw new IllegalArgumentException("Card id is required");
            }

            Optional<Card> card = cardService.findById(cardId);
            if (card.isEmpty()) {
                throw new IllegalArgumentException("Card not found: " + cardId);
            }

            cards.add(card.get());
        }

        return cards;
    }

    private List<Long> parseCardIds(String... cardIdValues) {
        List<Long> cardIds = new ArrayList<>();

        for (String cardIdValue : cardIdValues) {
            if (cardIdValue != null && !cardIdValue.isBlank()) {
                cardIds.add(Long.parseLong(cardIdValue));
            }
        }

        return cardIds;
    }

    private void validateDeckAccess(Deck deck, User user) {
        boolean isAdmin = user.getRole().equals(User.Role.ADMIN);
        boolean isOwner = deck.getUser() != null && deck.getUser().getUsername().equals(user.getUsername());

        if (!isOwner && !isAdmin) {
            throw new SecurityException("You do not have permission to modify this deck");
        }
    }
}