package es.codeujrc.distribuidos.DTOs;

import es.codeujrc.distribuidos.entity.User;

public record UserResponseDTO(
    Long id, 
    String username, 
    String email, 
    User.Role role
) {}