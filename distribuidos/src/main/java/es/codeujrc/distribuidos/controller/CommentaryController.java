package es.codeujrc.distribuidos.controller;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.service.CommentaryService;
import es.codeujrc.distribuidos.service.DeckService;
import es.codeujrc.distribuidos.service.UserService;
import java.util.Optional;

@Controller
public class CommentaryController {

    @Autowired
    private CommentaryService commentService;

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService; 

    @PostMapping("/commentDeck/{deckId}")
    public String addComment(@PathVariable Long deckId, 
                             @RequestParam String content, 
                             Principal principal) {
        
        if (principal != null && !content.isBlank()) {
            // Obtenemos el mazo y el usuario actual
            Optional<Deck> deck = deckService.findById(deckId);
            User user = userService.findByUsername(principal.getName());

            commentService.saveComment(deck, user, content);
        }

        return "redirect:/decks" ;
    }
}