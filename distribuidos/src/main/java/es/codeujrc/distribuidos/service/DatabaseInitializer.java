package es.codeujrc.distribuidos.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() throws IOException, URISyntaxException {
        // Sample users
        User Carlos = new User(1L, "carlos", passwordEncoder.encode("pass"), "algo@gmail.com", User.Role.USER);
        User adminuser = new User(2L, "admin", passwordEncoder.encode("adminpass"), "admin@gmail.com", User.Role.ADMIN);

		userRepository.save(Carlos);
		userRepository.save(adminuser);

		// Sample decks
		Deck deck1 = new Deck(1L, "Luffy", "Deck de Luffy", new ArrayList<>(), new ArrayList<>(), Carlos);
		Deck deck2 = new Deck(2L, "Zoro", "Deck de Zoro", new ArrayList<>(), new ArrayList<>(), adminuser);
		Deck deck3 = new Deck(3L, "Sanji", "Deck de Sanji", new ArrayList<>(), new ArrayList<>(), adminuser);

		deckService.save(deck1);
		deckService.save(deck2);
		deckService.save(deck3);

        // Sample cards
        Card card1 = new Card(1L, "Gomu Gomu no Pistol", "Luffy's signature move", deck1);
        



	}

	public void setBookImage(Book book, String classpathResource) throws IOException {
		Resource image = new ClassPathResource(classpathResource);

		Image createdImage = imageService.createImage(image.getInputStream());
		book.setImage(createdImage);
	}
}