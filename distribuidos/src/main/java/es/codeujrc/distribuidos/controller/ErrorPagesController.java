package es.codeujrc.distribuidos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPagesController {

    @GetMapping("/error/403")
    public String error403() {
        return "error/403";
    }
}