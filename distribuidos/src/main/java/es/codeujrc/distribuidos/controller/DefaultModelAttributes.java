package es.codeujrc.distribuidos.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultModelAttributes {

    @ModelAttribute
    public void addAttributes(Model model, Principal principal,  HttpServletRequest request) {
        if (principal != null) {
            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
        } else {
            model.addAttribute("logged", false);
            model.addAttribute("userName", null);
        }
    }
}