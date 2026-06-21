package es.codeujrc.distribuidos.restcontroller;

import es.codeujrc.distribuidos.DTOs.UserRequestDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new AuthResponse(AuthResponse.Status.FAILURE, "Ya tienes una sesion iniciada."));
        }

        try {
            return userLoginService.login(response, loginRequest);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(AuthResponse.Status.FAILURE, "Credenciales incorrectas"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequestDTO userRequestDTO) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new AuthResponse(AuthResponse.Status.FAILURE, "Ya tienes una sesion iniciada."));
        }

        User newUser = userMapper.toDomain(userRequestDTO);

        boolean isRegistered = userService.registerNewUser(newUser);

        if (!isRegistered) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(AuthResponse.Status.FAILURE, "El nombre de usuario o correo ya existen."));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(AuthResponse.Status.SUCCESS, "Usuario registrado correctamente."));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logOut(
            @CookieValue(name = "AuthToken", required = false) String token,
            HttpServletResponse response) {

        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(AuthResponse.Status.FAILURE, "No hay sesion activa."));
        }
        return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userLoginService.logout(response)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @CookieValue(name = "RefreshToken", required = false) String refreshToken,
            HttpServletResponse response) {

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(AuthResponse.Status.FAILURE, "No se ha proporcionado token de refresco."));
        }
        return userLoginService.refresh(response, refreshToken);
    }
}