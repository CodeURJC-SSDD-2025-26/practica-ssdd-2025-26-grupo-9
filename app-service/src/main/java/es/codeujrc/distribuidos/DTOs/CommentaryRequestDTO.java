package es.codeujrc.distribuidos.DTOs;

public record CommentaryRequestDTO(
    String content, 
    Long deckId
) {}