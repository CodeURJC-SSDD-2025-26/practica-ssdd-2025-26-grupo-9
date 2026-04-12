package es.codeujrc.distribuidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import es.codeujrc.distribuidos.security.UserSession;
import es.codeujrc.distribuidos.service.CardService;
import es.codeujrc.distribuidos.service.DeckService;
import es.codeujrc.distribuidos.entity.User;

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
}
