package es.codeujrc.distribuidos.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String error,
            @RequestParam(required = false) String errorlogin,
            @RequestParam(required = false) String registrationSuccess) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        if (errorlogin != null) {
            model.addAttribute("errorlogin", true);
        }
        if (registrationSuccess != null) {
            model.addAttribute("registrationSuccess", true);
        }

        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/social")
    public String social(Model model) {
        return "social";
    }

    @GetMapping("/adminUsers")
    public String adminUsers(Model model) {
        return "adminUsers";
    }

    @GetMapping("/editUserAdmin")
    public String editUserAdmin(Model model) {
        return "editUserAdmin";
    }

    @GetMapping("/editUser")
    public String editUser(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);
        }
        return "editUser";
    }

    @PostMapping("/register")
    public String registerUser(User newUser) {
        boolean isRegistered = userService.registerNewUser(newUser);
        if (!isRegistered) {
            return "redirect:/login?error=true";
        }
        return "redirect:/login?registrationSuccess=true";
    }

    @PostMapping("/editUser")
    public String updateProfile(Principal principal,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) MultipartFile imageFile) throws IOException {

        String currentUsername = principal.getName();
        User user = userService.findByUsername(currentUsername);
        userService.updateUser(user.getId(), username, email, password, imageFile);
        return "redirect:/profile";
    }

    @GetMapping("/user/{id}/image")
    public ResponseEntity<byte[]> downloadUserImage(@PathVariable long id) {
        byte[] image = userService.getUserImage(id);
        if (image != null && image.length > 0) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/png")
                    .body(image);
        }
        return ResponseEntity.notFound().build();
    }

}