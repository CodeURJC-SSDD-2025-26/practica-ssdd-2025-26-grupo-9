package es.codeujrc.distribuidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.codeujrc.distribuidos.security.UserSession;
import es.codeujrc.distribuidos.service.CardService;
import es.codeujrc.distribuidos.service.DeckService;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.Card;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DecksController {
    @Autowired
    private UserSession userSession;
    @Autowired
    private CardService cardService;
    @Autowired
    private DeckService deckService;

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
            RedirectAttributes redirectAttributes) {
        
        User currentUser = userSession.getUser();
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "Debes estar logueado para crear un mazo");
            return "redirect:/login";
        }

        // Crear el deck
        Deck newDeck = new Deck(name, description, new ArrayList<>(), new ArrayList<>(), currentUser);
        
        // Recopilar IDs de cartas
        List<Long> cardIds = new ArrayList<>();
        if (cardId1 != null && !cardId1.isEmpty()) cardIds.add(Long.parseLong(cardId1));
        if (cardId2 != null && !cardId2.isEmpty()) cardIds.add(Long.parseLong(cardId2));
        if (cardId3 != null && !cardId3.isEmpty()) cardIds.add(Long.parseLong(cardId3));
        if (cardId4 != null && !cardId4.isEmpty()) cardIds.add(Long.parseLong(cardId4));
        if (cardId5 != null && !cardId5.isEmpty()) cardIds.add(Long.parseLong(cardId5));
        if (cardId6 != null && !cardId6.isEmpty()) cardIds.add(Long.parseLong(cardId6));

        // Obtener las cartas
        List<Card> cards = new ArrayList<>();
        for (Long cardId : cardIds) {
            cardService.findById(cardId).ifPresent(cards::add);
        }

        // Asociar cartas con el deck
        newDeck.setCards(cards);
        
        // Guardar deck
        deckService.save(newDeck);
        
        redirectAttributes.addFlashAttribute("success", "¡Mazo creado correctamente!");
        return "redirect:/decks";
    }
}
