package es.codeujrc.distribuidos.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
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
        
        String fsPath = "src/main/resources/" + path;
        Resource fsResource = new FileSystemResource(fsPath);
        if (fsResource.exists()) {
            return fsResource.getContentAsByteArray();
        }
        
        return null; 
    }

    @PostConstruct
    public void init() throws IOException, URISyntaxException {
        // Sample users
        byte[] imgCarlos = loadImage("profileimages/ItoItoNoMi.jpg");
        byte[] imgAdmin = loadImage("profileimages/Admin.webp");

        User Carlos = new User("Carlos", passwordEncoder.encode("pass"), "algo@gmail.com", User.Role.REGISTERED,
                imgCarlos);
        User adminuser = new User("Admin", passwordEncoder.encode("adminpass"), "admin@gmail.com", User.Role.ADMIN,
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
        
        byte[] imgZoro = loadImage("cardimages/Zoro.png");
        Card CartaZoro1 = new Card("Zoro", "Description of Card 1", null, "Crew 1", 5, 5000, 5,
                Card.CardType.LEADER, Card.Atribute.SLASH, Card.Counter.NONE, Card.color.RED, new ArrayList<>(), imgZoro);

        byte[] imgNami = loadImage("cardimages/Nami.png");
        Card CartaNami2 = new Card("Nami", "Description of Card 3", "Trigger of Card 3", "Crew 3", 2, 0, 0,
                Card.CardType.CHARACTER, null, Card.Counter.NONE, Card.color.GREEN, new ArrayList<>(), imgNami);

        byte[] imgUsopp = loadImage("cardimages/Usopp.png");
        Card CartaUsopp3 = new Card("Usopp", "Description of Card 4", "Trigger of Card 4", "Crew 4", 1, 0, 0,
                Card.CardType.CHARACTER, null, Card.Counter.NONE, Card.color.YELLOW, new ArrayList<>(), imgUsopp);

        byte[] imgLuffy = loadImage("cardimages/Luffy.png");
        Card CartaLuffy4 = new Card("Luffy", "Description of Card 2", "Trigger of Card 2", "Crew 2", 3, 3000, 0,
                Card.CardType.LEADER, Card.Atribute.SLASH, Card.Counter.TWOTHOUSAND, Card.color.BLUE, new ArrayList<>(), imgLuffy);

        byte[] imgSanji = loadImage("cardimages/Sanji.png");
        Card CartaSanji5 = new Card("Sanji", "Description of Card 5", "Trigger of Card 5", "Crew 5", 4, 4000, 0,
                Card.CardType.CHARACTER, Card.Atribute.SLASH, Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), imgSanji);

        byte[] imgRobin = loadImage("cardimages/Robin.png");
        Card CartaRobin6 = new Card("Robin", "Description of Card 6", "Trigger of Card 6", "Crew 6", 8, 9000, 0,
                Card.CardType.CHARACTER, Card.Atribute.SLASH, Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), imgRobin);

        byte[] imgJimbe = loadImage("cardimages/Jimbe.png");
        Card CartaJimbe7 = new Card("Jimbe", "Description of Card 7", "Trigger of Card 7", "Crew 7", 6, 7000, 0,
                Card.CardType.CHARACTER, Card.Atribute.SLASH, Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), imgJimbe);
                
        byte[] imgBrook = loadImage("cardimages/Brook.png");
        Card CartaBrook8 = new Card("Brook", "El músico de la tripulación", "Trigger of Card 8", "Crew 8", 4, 4000, 0,
                Card.CardType.CHARACTER, Card.Atribute.SLASH, Card.Counter.TWOTHOUSAND, Card.color.GREEN, new ArrayList<>(), imgBrook);

        byte[] imgChopper = loadImage("cardimages/Chopper.png");
        Card CartaChopper9 = new Card("Chopper", "El médico de la tripulación", "Trigger of Card 9", "Crew 9", 2, 2000, 0,
                Card.CardType.CHARACTER, Card.Atribute.SLASH, Card.Counter.TWOTHOUSAND, Card.color.RED, new ArrayList<>(), imgChopper);

        byte[] imgFranky = loadImage("cardimages/Franky.png");
        Card CartaFranky10 = new Card("Franky", "El carpintero cyborg", "Trigger of Card 10", "Crew 10", 7, 7000, 0,
                Card.CardType.CHARACTER, Card.Atribute.SLASH, Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), imgFranky);

        byte[] imgShanks = loadImage("cardimages/Shanks.png");
        Card CartaShanks11 = new Card("Shanks", "Uno de los Cuatro Emperadores", "Trigger of Card 11", "Crew 11", 9, 10000, 0,
                Card.CardType.LEADER, Card.Atribute.SLASH, Card.Counter.NONE, Card.color.RED, new ArrayList<>(), imgShanks);

        byte[] imgMarco = loadImage("cardimages/Marco.png");
        Card CartaMarco12 = new Card("Marco", "Comandante de la primera división", "Trigger of Card 12", "Crew 12", 5, 6000, 0,
                Card.CardType.CHARACTER, Card.Atribute.SLASH, Card.Counter.TWOTHOUSAND, Card.color.YELLOW, new ArrayList<>(), imgMarco);


        
        cardService.save(CartaZoro1);
        cardService.save(CartaNami2);
        cardService.save(CartaUsopp3);
        cardService.save(CartaLuffy4);
        cardService.save(CartaSanji5);
        cardService.save(CartaRobin6);
        cardService.save(CartaJimbe7);
        cardService.save(CartaBrook8);
        cardService.save(CartaChopper9);
        cardService.save(CartaFranky10);
        cardService.save(CartaShanks11);
        cardService.save(CartaMarco12);

        deck1.setCards(Arrays.asList(CartaZoro1, CartaLuffy4, CartaBrook8, CartaChopper9, CartaFranky10, CartaShanks11));
        deck2.setCards(Arrays.asList(CartaNami2, CartaSanji5, CartaRobin6, CartaJimbe7, CartaMarco12, CartaBrook8));
        deck3.setCards(Arrays.asList(CartaUsopp3, CartaZoro1, CartaLuffy4, CartaSanji5, CartaShanks11, CartaFranky10));

        deckService.save(deck1);
        deckService.save(deck2);
        deckService.save(deck3);


        Commentary commentary1 = new Commentary("Great deck!", deck1, Carlos);
        Commentary commentary2 = new Commentary("Needs more power.", deck2, adminuser);

        commentaryService.save(commentary1);
        commentaryService.save(commentary2);
    }
}