package es.codeujrc.distribuidos.DTOs;

import java.util.List;

public record DeckDetailResponseDTO(
    Long id, 
    String name, 
    String description, 
    String formattedDate, 
    UserResponseDTO user, 
    List<CardImageResponseDTO> cards, 
    List<CommentaryResponseDTO> commentaries
) {}