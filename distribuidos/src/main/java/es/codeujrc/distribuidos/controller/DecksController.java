package es.codeujrc.distribuidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

import es.codeujrc.distribuidos.security.UserSession;
import es.codeujrc.distribuidos.service.CardService;
import es.codeujrc.distribuidos.service.DeckService;
import es.codeujrc.distribuidos.service.UserService;
import es.codeujrc.distribuidos.entity.User;

@Controller
public class DecksController {
    @Autowired
    private UserSession userSession;
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
}
