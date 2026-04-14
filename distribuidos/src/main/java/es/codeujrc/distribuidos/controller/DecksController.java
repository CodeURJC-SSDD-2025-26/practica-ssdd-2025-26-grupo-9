package es.codeujrc.distribuidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.Optional;
import es.codeujrc.distribuidos.service.CardService;
import es.codeujrc.distribuidos.service.DeckService;
import es.codeujrc.distribuidos.service.UserService;
import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.User;

@Controller
public class DecksController {
    @Autowired
    private CardService cardService;
    @Autowired
    private DeckService deckService;
    @Autowired
    private UserService userService;

    @GetMapping("/decks")
    public String listDecks(Model model) {
        model.addAttribute("decks", deckService.findAll());
        return "decks";
    }

    @GetMapping("/addDeck")
    public String addDeckForm(Model model) {
        model.addAttribute("cards", cardService.findAll());
        return "addDeck";
    }

    @GetMapping("/admindeck/{id}")
    public String editDeck(@PathVariable Long id, Model model, Principal principal) {

        Optional<Deck> optionalDeck = deckService.findById(id);
        if (!optionalDeck.isPresent()) {
            return "redirect:/profile";
        }
        Deck deck = optionalDeck.get();

        User user = userService.findByUsername(principal.getName());

        model.addAttribute("id", deck.getId());
        model.addAttribute("name", deck.getName());
        model.addAttribute("description", deck.getDescription());
        model.addAttribute("user", user);

        return "admindeck"; 
    }


    @PostMapping("/saveDeck")
    public String saveDeck(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam(required = false) String cardId1,
            @RequestParam(required = false) String cardId2,
            @RequestParam(required = false) String cardId3,
            @RequestParam(required = false) String cardId4,
            @RequestParam(required = false) String cardId5,
            @RequestParam(required = false) String cardId6,
            Model model,
            Principal principal) {
        
        if (principal == null) {
            model.addAttribute("error", "Debes estar logueado para crear un mazo");
            model.addAttribute("cards", cardService.findAll());
            return "addDeck";
        }

        User currentUser = userService.findByUsername(principal.getName());
        
        boolean success = deckService.createDeckWithCards(name, description, currentUser,
                cardId1, cardId2, cardId3, cardId4, cardId5, cardId6);
        
        if (!success) {
            model.addAttribute("error", "Error al crear el mazo. Verifica las cartas seleccionadas.");
            model.addAttribute("cards", cardService.findAll());
            return "addDeck";
        }
        
        model.addAttribute("success", "¡Mazo creado correctamente!");
        model.addAttribute("decks", deckService.findAll());
        return "decks";
    }
    
    @PostMapping("/deleteDeck/{id}")
    public String deleteDeck(@PathVariable Long id, Model model ) {

        deckService.delete(id);
        
        model.addAttribute("success", "¡Mazo eliminado correctamente!");
        model.addAttribute("decks", deckService.findAll());
        return "redirect:/profile";
    }
    
    @PostMapping("/editDeck/{id}")
    public String editDeck(@RequestParam(required = false) Long id, 
                       @RequestParam String name, 
                       @RequestParam String description) {
    Deck deck;
    if (id != null) {
       
        Optional<Deck> optionalDeck = deckService.findById(id);
        if (optionalDeck.isPresent()) {
            deck = optionalDeck.get();
        } else {
            
            return "redirect:/profile";
        }
    } else {
        deck = new Deck();
    }

    deck.setName(name);
    deck.setDescription(description);

    deckService.save(deck);

    return "redirect:/profile";
}
}
