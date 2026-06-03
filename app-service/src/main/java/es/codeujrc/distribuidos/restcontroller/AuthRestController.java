package es.codeujrc.distribuidos.restcontroller;

import es.codeujrc.distribuidos.DTOs.UserRequestDTO;
import es.codeujrc.distribuidos.DTOs.UserResponseDTO;
import es.codeujrc.distribuidos.DTOMappers.UserMapper;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.service.UserService;
import es.codeujrc.distribuidos.security.jwt.AuthResponse;
import es.codeujrc.distribuidos.security.jwt.LoginRequest;
import es.codeujrc.distribuidos.security.jwt.UserLoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            return userLoginService.login(response, loginRequest);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(AuthResponse.Status.FAILURE, "Credenciales incorrectas"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) throws IOException {
        
        User newUser = userMapper.toDomain(userRequestDTO);
        
        boolean isRegistered = userService.registerNewUser(newUser);
        
        if (!isRegistered) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponseDTO(newUser));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logOut(HttpServletResponse response) {
        return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userLoginService.logout(response)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @CookieValue(name = "RefreshToken", required = false) String refreshToken,
            HttpServletResponse response) {
        
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(AuthResponse.Status.FAILURE, "No RefreshToken cookie provided"));
        }
        return userLoginService.refresh(response, refreshToken);
    }
}