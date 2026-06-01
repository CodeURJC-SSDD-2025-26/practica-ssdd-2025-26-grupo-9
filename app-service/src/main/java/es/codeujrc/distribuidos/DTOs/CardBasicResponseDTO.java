package es.codeujrc.distribuidos.DTOs;

import es.codeujrc.distribuidos.entity.Card;

public record CardBasicResponseDTO(
    Long id, 
    String name, 
    int cost, 
    int power, 
    int health, 
    Card.CardType type, 
    Card.Atribute attribute, 
    Card.color color
) {}