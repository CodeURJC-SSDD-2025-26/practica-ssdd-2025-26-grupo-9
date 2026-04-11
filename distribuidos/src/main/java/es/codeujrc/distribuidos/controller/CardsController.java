package es.codeujrc.distribuidos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardsController {
    @GetMapping("/addCards")
    public String addCards(Model model) {
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
