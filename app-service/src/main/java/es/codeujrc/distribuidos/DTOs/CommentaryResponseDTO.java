package es.codeujrc.distribuidos.DTOs;

public record CommentaryResponseDTO(
    Long id, 
    String content, 
    UserBasicResponseDTO user
) {}