package es.codeujrc.distribuidos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.Card;
import es.codeujrc.distribuidos.entity.Commentary;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.repository.DeckRepository;

@Service
public class DeckService {

	@Autowired
	private DeckRepository deckRepository;

	@Autowired
	private CardService cardService;

	public List<Deck> findByUserId(Long userId) {
		return deckRepository.findByUserId(userId);
	}

	public Optional<Deck> findById(long id) {
		return deckRepository.findById(id);
	}

	public boolean exist(long id) {
		return deckRepository.existsById(id);
	}

	public List<Deck> findAll() {
		return deckRepository.findAll();
	}

	public void save(Deck deck) {
		deckRepository.save(deck);
	}

	public void delete(long id) {
		deckRepository.deleteById(id);
	}

	public boolean createDeckWithCards(String name, String description, User user,
			String cardId1, String cardId2, String cardId3, String cardId4, String cardId5, String cardId6) {

		Deck newDeck = new Deck(name, description, new ArrayList<>(), new ArrayList<>(), user);

		List<Long> cardIds = new ArrayList<>();
		if (cardId1 != null && !cardId1.isEmpty())
			cardIds.add(Long.parseLong(cardId1));
		if (cardId2 != null && !cardId2.isEmpty())
			cardIds.add(Long.parseLong(cardId2));
		if (cardId3 != null && !cardId3.isEmpty())
			cardIds.add(Long.parseLong(cardId3));
		if (cardId4 != null && !cardId4.isEmpty())
			cardIds.add(Long.parseLong(cardId4));
		if (cardId5 != null && !cardId5.isEmpty())
			cardIds.add(Long.parseLong(cardId5));
		if (cardId6 != null && !cardId6.isEmpty())
			cardIds.add(Long.parseLong(cardId6));

		List<Card> cards = new ArrayList<>();
		for (Long cardId : cardIds) {
			cardService.findById(cardId).ifPresent(cards::add);
		}

		if (cards.isEmpty()) {
			return false;
		}

		newDeck.setCards(cards);
		for (Card card : cards) {
			if (card.getDecks() == null) {
				card.setDecks(new ArrayList<>());
			}
			card.getDecks().add(newDeck);
		}

		save(newDeck);

		for (Card card : cards) {
			cardService.save(card);
		}

		return true;
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
}