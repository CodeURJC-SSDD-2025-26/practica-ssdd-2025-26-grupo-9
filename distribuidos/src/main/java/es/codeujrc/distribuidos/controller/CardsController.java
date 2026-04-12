package es.codeujrc.distribuidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import es.codeujrc.distribuidos.security.UserSession;
import es.codeujrc.distribuidos.service.CardService;
import es.codeujrc.distribuidos.entity.User;

@Controller
public class CardsController {

    @Autowired
    private UserSession userSession;
    @Autowired
    private CardService cardService;


    @GetMapping("/addCards")
    public String addCards(Model model) {
        model.addAttribute("cards", cardService.findAll()); // Aquí deberías cargar las cartas desde tu servicio
        return "addCards";
    }

    @GetMapping("/adminCard")
    public String adminCard(Model model) {
        return "adminCard";
    }

    @GetMapping("/cardDetail")
    public String cardDetail(Model model) {
        return "cardDetail";
    }
}
