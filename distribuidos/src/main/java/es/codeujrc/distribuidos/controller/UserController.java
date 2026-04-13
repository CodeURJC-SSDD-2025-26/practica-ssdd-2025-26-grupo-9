package es.codeujrc.distribuidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/editUser")
    public String editUser(Model model) {
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

}