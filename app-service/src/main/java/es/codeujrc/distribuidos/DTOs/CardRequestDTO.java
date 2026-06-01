package es.codeujrc.distribuidos.DTOs;

import es.codeujrc.distribuidos.entity.Card;

public record CardRequestDTO(
    String name, 
    String description, 
    String triggerEffect, 
    String crew, 
    int cost, 
    int power, 
    int health, 
    Card.CardType type, 
    Card.Atribute attribute, 
    Card.color color, 
    Card.Counter counter
) {}