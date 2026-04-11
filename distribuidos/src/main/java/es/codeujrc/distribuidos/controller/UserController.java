package es.codeujrc.distribuidos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
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
}