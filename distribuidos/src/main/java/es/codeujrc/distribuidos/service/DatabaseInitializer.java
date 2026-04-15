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
        // Leader type cards
        byte[] imgZoro = loadImage("cardimages/Zoro.png");
        Card GreenZoro = new Card("Zoro", "Description of Card 1", "Crew 1", 5, 5000, Card.Atribute.SLASH,
                Card.color.RED, new ArrayList<>(), imgZoro);

        // Event and stage type cards
        byte[] imgNami = loadImage("cardimages/Nami.png");
        Card EventCard = new Card("Nami", "Description of Card 3", "Trigger of Card 3", "Crew 3", 2,
                Card.CardType.EVENT, Card.color.GREEN, new ArrayList<>(), imgNami);

        byte[] imgUsopp = loadImage("cardimages/Usopp.png");
        Card StageCard = new Card("Usopp", "Description of Card 4", "Trigger of Card 4", "Crew 4", 1,
                Card.CardType.STAGE, Card.color.YELLOW, new ArrayList<>(), imgUsopp);

        // Character type cards
        byte[] imgLuffy = loadImage("cardimages/Luffy.png");
        Card CharacterCard = new Card("Luffy", "Description of Card 2", "Trigger of Card 2", "Crew 2", 3, 3000,
                Card.Atribute.SLASH, Card.Counter.TWOTHOUSAND, Card.color.BLUE, new ArrayList<>(), imgLuffy);

        byte[] imgSanji = loadImage("cardimages/Sanji.png");
        Card CharacterCard2 = new Card("Sanji", "Description of Card 5", "Trigger of Card 5", "Crew 5", 4, 4000,
                Card.Atribute.SLASH, Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), imgSanji);

        byte[] imgRobin = loadImage("cardimages/Robin.png");
        Card CharacterCard3 = new Card("Robin", "Description of Card 6", "Trigger of Card 6", "Crew 6", 8, 9000,
                Card.Atribute.SLASH, Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), imgRobin);

        byte[] imgJimbe = loadImage("cardimages/Jimbe.png");
        Card CharacterCard4 = new Card("Jimbe", "Description of Card 7", "Trigger of Card 7", "Crew 7", 6, 7000,
                Card.Atribute.SLASH, Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), imgJimbe);
                
        byte[] imgBrook = loadImage("cardimages/Brook.png");
        Card CharacterCard5 = new Card("Brook", "El músico de la tripulación", "Trigger of Card 8", "Crew 8", 4, 4000,
                Card.Atribute.SLASH, Card.Counter.TWOTHOUSAND, Card.color.GREEN, new ArrayList<>(), imgBrook);

        byte[] imgChopper = loadImage("cardimages/Chopper.png");
        Card CharacterCard6 = new Card("Chopper", "El médico de la tripulación", "Trigger of Card 9", "Crew 9", 2, 2000,
                Card.Atribute.SLASH, Card.Counter.TWOTHOUSAND, Card.color.RED, new ArrayList<>(), imgChopper);

        byte[] imgFranky = loadImage("cardimages/Franky.png");
        Card CharacterCard7 = new Card("Franky", "El carpintero cyborg", "Trigger of Card 10", "Crew 10", 7, 7000,
                Card.Atribute.SLASH, Card.Counter.NONE, Card.color.BLUE, new ArrayList<>(), imgFranky);

        byte[] imgShanks = loadImage("cardimages/Shanks.png");
        Card CharacterCard8 = new Card("Shanks", "Uno de los Cuatro Emperadores", "Trigger of Card 11", "Crew 11", 9, 10000,
                Card.Atribute.SLASH, Card.Counter.NONE, Card.color.RED, new ArrayList<>(), imgShanks);

        byte[] imgMarco = loadImage("cardimages/Marco.png");
        Card CharacterCard9 = new Card("Marco", "Comandante de la primera división", "Trigger of Card 12", "Crew 12", 5, 6000,
                Card.Atribute.SLASH, Card.Counter.TWOTHOUSAND, Card.color.YELLOW, new ArrayList<>(), imgMarco);

        cardService.save(GreenZoro);
        cardService.save(EventCard);
        cardService.save(StageCard);
        cardService.save(CharacterCard);
        cardService.save(CharacterCard2);
        cardService.save(CharacterCard3);
        cardService.save(CharacterCard4);
        cardService.save(CharacterCard5);
        cardService.save(CharacterCard6);
        cardService.save(CharacterCard7);
        cardService.save(CharacterCard8);
        cardService.save(CharacterCard9);

        deck1.setCards(Arrays.asList(GreenZoro, CharacterCard, CharacterCard5, CharacterCard6, CharacterCard7, CharacterCard8));
        deck2.setCards(Arrays.asList(EventCard, CharacterCard2, CharacterCard3, CharacterCard4, CharacterCard9, CharacterCard5));
        deck3.setCards(Arrays.asList(StageCard, GreenZoro, CharacterCard, CharacterCard2, CharacterCard8, CharacterCard7));

        deckService.save(deck1);
        deckService.save(deck2);
        deckService.save(deck3);


        Commentary commentary1 = new Commentary("Great deck!", deck1, Carlos);
        Commentary commentary2 = new Commentary("Needs more power.", deck2, adminuser);

        commentaryService.save(commentary1);
        commentaryService.save(commentary2);
    }
}