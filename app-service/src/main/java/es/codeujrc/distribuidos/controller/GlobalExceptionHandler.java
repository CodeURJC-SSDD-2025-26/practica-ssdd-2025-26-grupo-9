package es.codeujrc.distribuidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(ResponseStatusException ex, HttpServletResponse response, Model model) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        response.setStatus(status.value());
        model.addAttribute("message", ex.getReason());

        if (status == HttpStatus.NOT_FOUND) {
            return "error/404";
        }
        if (status == HttpStatus.FORBIDDEN) {
            return "error/403";
        }
        return "error/500";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex, HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return "error/403";
    }
}