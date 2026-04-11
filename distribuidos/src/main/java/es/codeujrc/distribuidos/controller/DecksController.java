package es.codeujrc.distribuidos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DecksController {

    @GetMapping("/decks")
    public String listDecks(Model model) {
        return "decks";
    }

    @GetMapping("/addDeck")
    public String addDeckForm() {
        return "addDeck";
    }
}
