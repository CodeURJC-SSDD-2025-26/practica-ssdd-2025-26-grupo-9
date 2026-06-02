package es.codeujrc.distribuidos.DTOMappers;

import es.codeujrc.distribuidos.DTOs.CardBasicResponseDTO;
import es.codeujrc.distribuidos.DTOs.CardDetailResponseDTO;
import es.codeujrc.distribuidos.DTOs.CardImageResponseDTO;
import es.codeujrc.distribuidos.DTOs.CardRequestDTO;
import es.codeujrc.distribuidos.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardImageResponseDTO toImageDTO(Card card);
    List<CardImageResponseDTO> toImageDTOs(Collection<Card> cards);

    CardBasicResponseDTO toBasicDTO(Card card);
    List<CardBasicResponseDTO> toBasicDTOs(Collection<Card> cards);

    CardDetailResponseDTO toDetailDTO(Card card);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "decks", ignore = true)
    Card toDomain(CardRequestDTO cardRequestDTO);
}