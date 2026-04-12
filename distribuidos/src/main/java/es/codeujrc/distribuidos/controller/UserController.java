package es.codeujrc.distribuidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.security.UserSession;
import es.codeujrc.distribuidos.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserSession userSession;

    @ModelAttribute
    public void addAttributes(Model model) {
        if (userSession.isLogged()) {
            model.addAttribute("logged", true);
            model.addAttribute("userName", userSession.getUser().getUsername());
            model.addAttribute("isAdmin", userSession.getUser().getRole() == User.Role.ADMIN);
        } else {
            model.addAttribute("logged", false);
        }
    }

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

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password) {

        User user = userService.login(email, password);

        if (user != null) {
            userSession.setUser(user);
            return "redirect:/";
        }

        return "redirect:/login?errorlogin=true";
    }
}