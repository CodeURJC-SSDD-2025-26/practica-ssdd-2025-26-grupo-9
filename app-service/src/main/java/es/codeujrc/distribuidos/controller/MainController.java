package es.codeujrc.distribuidos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import es.codeujrc.distribuidos.service.CardService;
import java.util.Map;

@Controller
public class MainController {
    @Autowired 
    private CardService cardService;


    @GetMapping("/")
    public String home(Model model) {
        Map <String, Object> chartData = cardService.getMetaCardsData();

        model.addAttribute("cardNames", chartData.get("names"));
        model.addAttribute("deckCounts", chartData.get("counts"));
        return "home";
    }
}