package es.codeujrc.distribuidos.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

	private byte[] loadImage(String path) throws IOException {
		Resource imageRes = new ClassPathResource(path);
		if (imageRes.exists()) {
			return imageRes.getContentAsByteArray();
		}
		return null; // O una imagen por defecto
	}

	@PostConstruct
	public void init() throws IOException, URISyntaxException {
		// Sample users
		byte[] imgCarlos = loadImage("profileimages/ItoItoNoMi.jpg");
		byte[] imgAdmin = loadImage("profileimages/GomuGomuNoMi.jpg");

		User Carlos = new User("Carlos", passwordEncoder.encode("pass"), "algo@gmail.com", User.Role.REGISTERED,
				imgCarlos);
		User adminuser = new User("xXElAdmin360Xx", passwordEncoder.encode("adminpass"), "admin@gmail.com", User.Role.ADMIN,
				imgAdmin);

		userRepository.save(Carlos);
		userRepository.save(adminuser);

		// Sample decks
		Deck deck1 = new Deck("Luffy", "Deck de Luffy", new ArrayList<>(), new ArrayList<>(), Carlos);
		Deck deck2 = new Deck("Zoro", "Deck de Zoro", new ArrayList<>(), new ArrayList<>(), adminuser);
		Deck deck3 = new Deck("Sanji", "Deck de Sanji", new ArrayList<>(), new ArrayList<>(), adminuser);

		deckService.save(deck1);
		deckService.save(deck2);
		deckService.save(deck3);

		// Sample cards
		// Leader type cards
		byte[] imgZoro = loadImage("cardimages/Zoro.png");
		Card GreenZoro = new Card("Card 1", "Description of Card 1", "Crew 1", 5, 5000, Card.Atribute.SLASH,
				Card.color.RED, new ArrayList<>(), imgZoro);

		// Event and stage type cards
		byte[] imgNami = loadImage("cardimages/Nami.png");
		Card EventCard = new Card("Card 3", "Description of Card 3", "Trigger of Card 3", "Crew 3", 2,
				Card.CardType.EVENT, Card.color.GREEN, new ArrayList<>(), imgNami);

		byte[] imgUsopp = loadImage("cardimages/Usopp.png");
		Card StageCard = new Card("Card 4", "Description of Card 4", "Trigger of Card 4", "Crew 4", 1,
				Card.CardType.STAGE, Card.color.YELLOW, new ArrayList<>(), imgUsopp);

		// Character type cards
		byte[] imgLuffy = loadImage("cardimages/Luffy.png");
		Card CharacterCard = new Card("Card 2", "Description of Card 2", "Trigger of Card 2", "Crew 2", 3, 3000,
				Card.Atribute.SLASH, Card.Counter.TWOTHOUSAND, Card.color.BLUE, new ArrayList<>(), imgLuffy);

		byte[] imgSanji = loadImage("cardimages/Sanji.png");
		Card CharacterCard2 = new Card("Card 5", "Description of Card 5", "Trigger of Card 5", "Crew 5", 4, 4000,
				Card.Atribute.SLASH, Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), imgSanji);

		byte[] imgRobin = loadImage("cardimages/Robin.png");
		Card CharacterCard3 = new Card("Card 6", "Description of Card 6", "Trigger of Card 6", "Crew 6", 8, 9000,
				Card.Atribute.SLASH, Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), imgRobin);

		cardService.save(GreenZoro);
		cardService.save(EventCard);
		cardService.save(StageCard);
		cardService.save(CharacterCard);
		cardService.save(CharacterCard2);
		cardService.save(CharacterCard3);


		deck1.setCards(Arrays.asList(GreenZoro, CharacterCard));
		deck2.setCards(Arrays.asList(EventCard, CharacterCard));
		deck3.setCards(Arrays.asList(StageCard));

		deckService.save(deck1);
		deckService.save(deck2);
		deckService.save(deck3);


		Commentary commentary1 = new Commentary("Great deck!", deck1, Carlos);
		Commentary commentary2 = new Commentary("Needs more power.", deck2, adminuser);

		commentaryService.save(commentary1);
		commentaryService.save(commentary2);
	}

}