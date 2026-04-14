package es.codeujrc.distribuidos.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import es.codeujrc.distribuidos.entity.Card;   
import es.codeujrc.distribuidos.security.UserSession;
import es.codeujrc.distribuidos.service.CardService;
import es.codeujrc.distribuidos.entity.User;

@Controller
public class CardsController {

    @Autowired
    private UserSession userSession;
    @Autowired
    private CardService cardService;

    @ModelAttribute
    public void addAttributes(Model model) {
        if (userSession.isLogged()) {
            model.addAttribute("logged", true);
            model.addAttribute("userName", userSession.getUser().getUsername());
            model.addAttribute("isAdmin", userSession.getUser().getRole() == User.Role.ADMIN);
        } else {
            model.addAttribute("logged", false);
        }
    }

    @GetMapping("/addCards")
    public String addCards(Model model) {
        model.addAttribute("cards", cardService.findAll());
        return "addCards";
    }

    @GetMapping("/adminCard")
    public String adminCard(Model model, @RequestParam(required = false) Long id) {
    if (id != null) {
        // Si editamos, buscamos la carta
        Card card = cardService.findById(id).orElseThrow();
        model.addAttribute("card", card);
    } else {
        // SI ES NUEVA: Pasamos un objeto vacío. 
        // Si no haces esto, el HTML "peta" al no encontrar {{card}}
        model.addAttribute("card", new Card());
    }
    return "adminCard";
}

    @GetMapping("/cardDetail")
    public String cardDetail(Model model) {
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

