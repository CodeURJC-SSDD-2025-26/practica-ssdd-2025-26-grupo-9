package es.codeujrc.distribuidos.DTOMappers;

import es.codeujrc.distribuidos.DTOs.PDFCardDTO;
import es.codeujrc.distribuidos.DTOs.PDFDeckDTO;
import es.codeujrc.distribuidos.entity.Card;
import es.codeujrc.distribuidos.entity.Deck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PDFMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "userImage", source = "user.image")
    @Mapping(target = "deckName", source = "name")
    @Mapping(target = "deckDescription", source = "description")
    PDFDeckDTO toDTO(Deck deck);

    PDFCardDTO toCardDTO(Card card);

    List<PDFDeckDTO> toDTOList(List<Deck> decks);
}