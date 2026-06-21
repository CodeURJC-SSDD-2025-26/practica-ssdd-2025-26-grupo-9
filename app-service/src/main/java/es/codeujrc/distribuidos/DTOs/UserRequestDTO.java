package es.codeujrc.distribuidos.DTOs;


public record UserRequestDTO(
    String username, 
    String password, 
    String email, 
    String role
) {}