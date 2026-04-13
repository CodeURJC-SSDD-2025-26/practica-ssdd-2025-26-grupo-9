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

import org.springframework.security.crypto.password.PasswordEncoder;

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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() throws IOException, URISyntaxException {
        // Sample users
        User Carlos = new User("carlos", passwordEncoder.encode("pass"), "algo@gmail.com", User.Role.REGISTERED, null);
		User adminuser = new User("admin", passwordEncoder.encode("adminpass"), "admin@gmail.com", User.Role.ADMIN, null);

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
        Card GreenZoro = new Card("Card 1", "Description of Card 1","Crew 1", 5, 5000, Card.Atribute.SLASH, Card.color.RED, new ArrayList<>(), null);
        // Event and stage type cards
		Card EventCard = new Card("Card 3", "Description of Card 3", "Trigger of Card 3", "Crew 3", 2, Card.CardType.EVENT, Card.color.GREEN, new ArrayList<>(), null);
		Card StageCard = new Card("Card 4", "Description of Card 4", "Trigger of Card 4", "Crew 4", 1, Card.CardType.STAGE, Card.color.YELLOW, new ArrayList<>(), null);
		// Character type cards
		Card CharacterCard = new Card("Card 2", "Description of Card 2", "Trigger of Card 2", "Crew 2", 3, 3000, Card.Atribute.SLASH,Card.Counter.TWOTHOUSAND, Card.color.BLUE, new ArrayList<>(), null);
		Card CharacterCard2 = new Card("Card 5", "Description of Card 5", "Trigger of Card 5", "Crew 5", 4, 4000, Card.Atribute.SLASH,Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), null);
		Card CharacterCard3 = new Card("Card 6", "Description of Card 6", "Trigger of Card 6", "Crew 6", 8, 9000, Card.Atribute.SLASH,Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), null);
		cardService.save(GreenZoro);
		cardService.save(EventCard);
		cardService.save(StageCard);
		cardService.save(CharacterCard);
		cardService.save(CharacterCard2);
		cardService.save(CharacterCard3);

		// Associate cards with decks
		deck1.setCards(Arrays.asList(GreenZoro, CharacterCard));
		deck2.setCards(Arrays.asList(EventCard, CharacterCard));
		deck3.setCards(Arrays.asList(StageCard));
		
		deckService.save(deck1);
		deckService.save(deck2);
		deckService.save(deck3);

		// Sample commentaries
		Commentary commentary1 = new Commentary( "Great deck!", deck1, Carlos);
		Commentary commentary2 = new Commentary( "Needs more power.", deck2, adminuser);

		commentaryService.save(commentary1);
		commentaryService.save(commentary2);

	}


}