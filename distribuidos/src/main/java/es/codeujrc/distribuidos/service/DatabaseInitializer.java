package es.codeujrc.distribuidos.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeujrc.distribuidos.entity.Card;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.Commentary;

import es.codeujrc.distribuidos.repository.*;

@Service
public class DatabaseInitializer {

	@Autowired
	private CardService cardService;

	@Autowired
	private DeckService deckService;

	@Autowired
	private CommentaryService commentaryService;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init() throws IOException, URISyntaxException {
        // Sample users
        User Carlos = new User( "carlos", "pass", "algo@gmail.com", User.Role.REGISTERED, null);
        User adminuser = new User( "admin", "adminpass", "admin@gmail.com", User.Role.ADMIN, null);

		userRepository.save(Carlos);
		userRepository.save(adminuser);

		// Sample decks
		Deck deck1 = new Deck( "Luffy", "Deck de Luffy", new ArrayList<>(), new ArrayList<>(), Carlos);
		Deck deck2 = new Deck( "Zoro", "Deck de Zoro", new ArrayList<>(), new ArrayList<>(), adminuser);
		Deck deck3 = new Deck( "Sanji", "Deck de Sanji", new ArrayList<>(), new ArrayList<>(), adminuser);

		deckService.save(deck1);
		deckService.save(deck2);
		deckService.save(deck3);

        // Sample cards
		// Leader type cards
        Card GreenZoro = new Card("Card 1", "Description of Card 1","Crew 1", 5, 5000, Card.Atribute.SLASH, Card.color.RED, Arrays.asList(deck1), null);
        // Event and stage type cards
		Card EventCard = new Card("Card 3", "Description of Card 3", "Trigger of Card 3", "Crew 3", 2, Card.CardType.EVENT, Card.color.GREEN, Arrays.asList(deck2), null);
		Card StageCard = new Card("Card 4", "Description of Card 4", "Trigger of Card 4", "Crew 4", 1, Card.CardType.STAGE, Card.color.YELLOW, Arrays.asList(deck3), null);
		// Character type cards
		Card CharacterCard = new Card("Card 2", "Description of Card 2", "Trigger of Card 2", "Crew 2", 3, Card.CardType.CHARACTER, Card.color.BLUE, Arrays.asList(deck1, deck2), null);

		cardService.save(GreenZoro);
		cardService.save(EventCard);
		cardService.save(StageCard);
		cardService.save(CharacterCard);

		// Sample commentaries
		Commentary commentary1 = new Commentary( "Great deck!", deck1, Carlos);
		Commentary commentary2 = new Commentary( "Needs more power.", deck2, adminuser);

		commentaryService.save(commentary1);
		commentaryService.save(commentary2);

	}


}