package es.codeujrc.distribuidos.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import es.codeujrc.distribuidos.entity.Card;
import es.codeujrc.distribuidos.service.CardService;


@Controller
public class CardsController {

    
    @Autowired
    private CardService cardService;

    

    @GetMapping("/addCards")
    public String addCards(Model model) {
        model.addAttribute("cards", cardService.findAll());
        return "addCards";
    }

    @GetMapping("/adminCard")
    public String adminCard(Model model, @RequestParam(required = false) Long id) {
    if (id != null) {

        Card card = cardService.findById(id).orElseThrow();
        model.addAttribute("card", card);
    } else {

        model.addAttribute("card", new Card());
    }
    return "adminCard";
    }

    @GetMapping("/cardDetail")
    public String cardDetail(Model model, @RequestParam(required = false) Long id) {

        Card card = cardService.findById(id).orElseThrow();
        model.addAttribute("card", card);

    return "cardDetail";
    }

    @GetMapping("/card/{id}/image")
    public ResponseEntity<byte[]> downloadCardImage(@PathVariable long id) {
        byte[] image = cardService.getCardImage(id);
        if (image != null && image.length > 0) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/png")
                    .body(image);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/deleteCard/{id}")
    public String deleteCard(@PathVariable Long id) {

        cardService.delete(id);
        return "redirect:/addCards";
    }
    @PostMapping("/saveCard")
    public String saveCard(Card card, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (imageFile != null && !imageFile.isEmpty()) {
            card.setImage(imageFile.getBytes());
        }
        cardService.save(card);
        return "redirect:/addCards";
    }
}

