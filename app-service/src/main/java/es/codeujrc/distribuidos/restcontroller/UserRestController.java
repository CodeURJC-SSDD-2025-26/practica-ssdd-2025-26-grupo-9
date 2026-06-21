package es.codeujrc.distribuidos.restcontroller;

import es.codeujrc.distribuidos.DTOs.CommentaryRequestDTO;
import es.codeujrc.distribuidos.DTOs.CommentaryResponseDTO;
import es.codeujrc.distribuidos.DTOs.UserBasicResponseDTO;
import es.codeujrc.distribuidos.DTOs.UserRequestDTO;
import es.codeujrc.distribuidos.DTOs.UserResponseDTO;
import es.codeujrc.distribuidos.DTOMappers.CommentaryMapper;
import es.codeujrc.distribuidos.DTOMappers.UserMapper;
import es.codeujrc.distribuidos.entity.Commentary;
import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.service.CommentaryService;
import es.codeujrc.distribuidos.service.DeckService;
import es.codeujrc.distribuidos.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import es.codeujrc.distribuidos.service.PDFService;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    
    @Autowired
    private CommentaryService commentaryService;

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PDFService pdfService;

    @GetMapping("/admin")
    public ResponseEntity<Page<UserResponseDTO>> getUsersAdmin(Pageable pageable) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentUsername == null || currentUsername.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User currentUser = userService.findByUsername(currentUsername);
        boolean isAdmin = currentUser.getRole().equals(User.Role.ADMIN);
        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        Page<User> usersPage = userService.findAll(pageable);
        Page<UserResponseDTO> dtoPage = usersPage.map(userMapper::toResponseDTO);
        return ResponseEntity.ok(dtoPage);
    }
    
    @GetMapping("")
    public ResponseEntity<Page<UserBasicResponseDTO>> getUsers(Pageable pageable) {
        Page<User> usersPage = userService.findAll(pageable);
        Page<UserBasicResponseDTO> dtoPage = usersPage.map(userMapper::toBasicResponseDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @PutMapping("/admin/{userId}")
    public ResponseEntity<UserResponseDTO> editUsersAdmin(
            @PathVariable Long userId,
            @RequestBody UserRequestDTO requestDTO) throws IOException {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentUsername == null || currentUsername.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User currentUser = userService.findByUsername(currentUsername);
        boolean isAdmin = currentUser.getRole().equals(User.Role.ADMIN);

        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!userService.exist(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        userService.updateUser(userId, requestDTO.username(), requestDTO.email(), requestDTO.password(), requestDTO.role());

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponseDTO(userService.findById(userId)));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> editUsers(
            @PathVariable Long userId,
            @RequestBody UserRequestDTO requestDTO) throws IOException {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentUsername == null || currentUsername.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User currentUser = userService.findByUsername(currentUsername);
        boolean isSameUser = currentUser.getId().equals(userId);
        if (!userService.exist(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!isSameUser) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.updateUser(userId, requestDTO.username(), requestDTO.email(), requestDTO.password(), (org.springframework.web.multipart.MultipartFile) null);

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponseDTO(userService.findById(userId)));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> deleteUser(
            @PathVariable Long userId) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentUsername == null || currentUsername.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!userService.exist(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = userService.findById(userId);
        User currentUser = userService.findByUsername(currentUsername);
        boolean isAdmin = currentUser.getRole().equals(User.Role.ADMIN);

        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        UserResponseDTO responseDTO = userMapper.toResponseDTO(user);
        userService.delete(userId);

        return ResponseEntity.ok(responseDTO);
    }

}
