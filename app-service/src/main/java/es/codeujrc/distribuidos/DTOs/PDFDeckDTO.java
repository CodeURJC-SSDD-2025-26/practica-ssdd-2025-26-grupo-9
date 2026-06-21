package es.codeujrc.distribuidos.DTOs;

import java.util.List;

public record PDFDeckDTO(
    String username,
    byte[] userImage,
    String deckName,
    String deckDescription,
    List<PDFCardDTO> cards
) {}