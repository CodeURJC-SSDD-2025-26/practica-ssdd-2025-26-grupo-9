package es.codeujrc.distribuidos.restcontroller;

import es.codeujrc.distribuidos.DTOs.CommentaryRequestDTO;
import es.codeujrc.distribuidos.DTOs.CommentaryResponseDTO;
import es.codeujrc.distribuidos.DTOs.UserResponseDTO;
import es.codeujrc.distribuidos.DTOMappers.CommentaryMapper;
import es.codeujrc.distribuidos.DTOMappers.UserMapper;
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
@RequestMapping("/api/v1/users/{userId}")
public class UserRestController {
    
    @Autowired
    private CommentaryService commentaryService;

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    //@GetMapping("/")
    //public ResponseEntity<Page<UserResponseDTO>> getUsers(
            //@PathVariable Long userId,
            //Pageable pageable) {
        
        //if (!userService.findById(userId).isPresent()) {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        //}

        //Page<User> usersPage = userService.findByUserIdPaginated(userId, pageable);
        
        //Page<UserResponseDTO> dtoPage = usersPage.map(userMapper::toResponseDTO);

        //return ResponseEntity.ok(dtoPage);
    //}
}
