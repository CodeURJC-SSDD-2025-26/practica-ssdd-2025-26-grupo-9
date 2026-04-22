package es.codeujrc.distribuidos.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.entity.User;
import es.codeujrc.distribuidos.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import es.codeujrc.distribuidos.service.CardService;
import es.codeujrc.distribuidos.service.DeckService;
import es.codeujrc.distribuidos.service.PDFService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private DeckService deckService;
    @Autowired
    private PDFService pdfService;

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
    public String profile(Model model, Principal principal, @RequestParam(required = false) boolean userConflict,
            @RequestParam(required = false) boolean emailConflict) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("usernameError", userConflict);
        model.addAttribute("emailError", emailConflict);
        model.addAttribute("cards", cardService.findAll());
        model.addAttribute("decks", deckService.findByUserId(user.getId()));
        return "profile";
    }

    @GetMapping("/social")
    public String social(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("followed", new ArrayList<User>());
            model.addAttribute("unfollowed", new ArrayList<User>());
            model.addAttribute("followedDecks", new ArrayList<Deck>());
            return "social";
        }

        User currentUser = userService.findByUsername(principal.getName());
        List<User> followed = currentUser.getFollowing();
        List<User> allUsers = userService.findAll();
        List<User> unfollowed = new ArrayList<>();

        for (User u : allUsers) {
            if (!u.getId().equals(currentUser.getId()) && !followed.contains(u)) {
                unfollowed.add(u);
            }
        }
        List<Deck> followedDecks = deckService.getDecksFromFollowing(currentUser);

        model.addAttribute("followed", followed);
        model.addAttribute("unfollowed", unfollowed);
        model.addAttribute("followedDecks", followedDecks);
        return "social";
    }

    @GetMapping("/adminUsers")
    public String adminUsers(Model model, Principal principal,
            @RequestParam(required = false) boolean userConflict,
            @RequestParam(required = false) boolean emailConflict) {
        List<User> users = userService.findAll();
        String currentUsername = principal.getName();
        List<Pair<User, Boolean>> usersForView = new ArrayList<>();
        for (User user : users) {
            boolean isSelf = user.getUsername().equals(currentUsername);
            usersForView.add(Pair.of(user, isSelf));
        }
        model.addAttribute("users", usersForView);
        model.addAttribute("userConflict", userConflict);
        model.addAttribute("emailConflict", emailConflict);
        return "adminUsers";
    }

    @GetMapping("/editUserAdmin/{id}")
    public String editUserAdmin(Model model, @PathVariable long id) {
        User userToEdit = userService.findById(id);
        model.addAttribute("user", userToEdit);
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
    public String registerUser(User newUser) throws IOException {
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
        Pair<Boolean, Boolean> result = userService.updateUser(user.getId(), username, email, password, imageFile);
        return "redirect:/profile?userConflict=" + result.getFirst() + "&emailConflict=" + result.getSecond();
    }

    @PostMapping("/editUserAdmin")
    public String editUserAdmin(
            @RequestParam long id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) MultipartFile imageFile) throws IOException {
        Pair<Boolean, Boolean> result = userService.updateUserAsAdmin(id, username, email, password, role, imageFile);
        if (result.getFirst() || result.getSecond()) {
            return "redirect:/adminUsers/" + id + "?userConflict=" + result.getFirst() + "&emailConflict="
                    + result.getSecond();
        }
        return "redirect:/adminUsers";
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

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.delete(id);
        return "redirect:/adminUsers";
    }

    @PostMapping("/user/{id}/follow")
    public String followUser(@PathVariable long id, Principal principal) {
        if (principal != null) {
            User currentUser = userService.findByUsername(principal.getName());
            userService.follow(currentUser.getId(), id);
        }
        return "redirect:/social";
    }

    @PostMapping("/user/{id}/unfollow")
    public String unfollowUser(@PathVariable long id, Principal principal) {
        if (principal != null) {
            User currentUser = userService.findByUsername(principal.getName());
            userService.unfollow(currentUser.getId(), id);
        }
        return "redirect:/social";
    }

    //Tecnologia extra de los pdfs
    @GetMapping("/downloadMyDecks")
    public void downloadMyDecks(HttpServletResponse response, Principal principal) throws IOException {
        if (principal == null) {
            response.sendRedirect("/login");
            return;
        }

        User user = userService.findByUsername(principal.getName());
        List<Deck> myDecks = deckService.findByUserId(user.getId());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Mis_Mazos.pdf");

        pdfService.exportDecksToPdf(myDecks, response.getOutputStream());
    }

}