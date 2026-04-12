package es.codeujrc.distribuidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import es.codeujrc.distribuidos.security.UserSession;
import es.codeujrc.distribuidos.entity.User;

@ControllerAdvice
public class DefaultModelAttributes {

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
            model.addAttribute("isAdmin", false);
        }
    }
}