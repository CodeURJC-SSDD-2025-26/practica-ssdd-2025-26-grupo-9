package es.codeujrc.distribuidos.restcontroller;

import es.codeujrc.distribuidos.DTOs.CommentaryRequestDTO;
import es.codeujrc.distribuidos.DTOs.CommentaryResponseDTO;
import es.codeujrc.distribuidos.DTOMappers.CommentaryMapper;
import es.codeujrc.distribuidos.DTOMappers.DeckMapper;
import es.codeujrc.distribuidos.entity.Commentary;
import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.service.CommentaryService;
import es.codeujrc.distribuidos.service.DeckService;
import es.codeujrc.distribuidos.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/decks/{deckId}")
public class DeckRestController {
    
    @Autowired
    private CommentaryService commentaryService;

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeckMapper deckMapper;
}
