package es.codeujrc.distribuidos.restcontroller;

import es.codeujrc.distribuidos.DTOs.UserBasicResponseDTO;
import es.codeujrc.distribuidos.DTOs.UserRequestDTO;
import es.codeujrc.distribuidos.DTOs.UserResponseDTO;
import es.codeujrc.distribuidos.DTOMappers.PDFMapper;
import es.codeujrc.distribuidos.DTOMappers.UserMapper;
import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.service.DeckService;
import es.codeujrc.distribuidos.service.UserService;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PDFMapper pdfMapper;

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

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        Optional<UserResponseDTO> user = Optional.ofNullable(userService.findById(userId)).map(userMapper::toResponseDTO);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        return ResponseEntity.ok(user.get());
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
        
        if (requestDTO.imageBase64() != null && !requestDTO.imageBase64().isBlank()) {
            userService.updateUserImage(userId, requestDTO.imageBase64());
        }

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
        
        if (requestDTO.imageBase64() != null && !requestDTO.imageBase64().isBlank()) {
            userService.updateUserImage(userId, requestDTO.imageBase64());
        }

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

    @GetMapping("/{userId}/MyDecksPdf")
    public ResponseEntity<byte[]> downloadMyDecksAPI(@PathVariable Long userId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findByUsername(principal.getName());
        if (!user.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Deck> myDecks = deckService.findByUserId(user.getId());
        List<es.codeujrc.distribuidos.DTOs.PDFDeckDTO> dtoList = pdfMapper.toDTOList(myDecks);
        
        try {
            org.springframework.web.client.RestClient restClient = org.springframework.web.client.RestClient.create();
            byte[] pdfBytes = restClient.post()
                    .uri("http://localhost:8080/api/v1/utilities/pdf")
                    .body(dtoList)
                    .retrieve()
                    .body(byte[].class);

            return ResponseEntity.ok()
                    .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Mis_Mazos.pdf")
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
                    
        } catch (Exception e) {
            System.err.println("Fallo al conectar con utility-service: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{userId}/image")
    public ResponseEntity<?> getUserImage(@PathVariable Long userId) {
        Optional<byte[]> image = userService.getImage(userId);

        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User image not found"));
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(image.get());
    }

}
