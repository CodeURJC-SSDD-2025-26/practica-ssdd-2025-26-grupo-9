package es.codeujrc.distribuidos.DTOs;

import java.util.List;

public record DeckRequestDTO(
    String name, 
    String description, 
    List<Long> cardIds
) {}