package es.codeujrc.distribuidos.DTOs;

import es.codeujrc.distribuidos.entity.User;

public record UserRequestDTO(
    String username, 
    String password, 
    String email, 
    String role
) {}