package es.codeujrc.distribuidos.DTOMappers;

import es.codeujrc.distribuidos.DTOs.DeckBasicResponseDTO;
import es.codeujrc.distribuidos.DTOs.DeckDetailResponseDTO;
import es.codeujrc.distribuidos.DTOs.DeckRequestDTO;
import es.codeujrc.distribuidos.entity.Deck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CardMapper.class, CommentaryMapper.class})
public interface DeckMapper {

    DeckBasicResponseDTO toBasicDTO(Deck deck);
    List<DeckBasicResponseDTO> toBasicDTOs(Collection<Deck> decks);

    DeckDetailResponseDTO toDetailDTO(Deck deck);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cards", ignore = true)
    @Mapping(target = "commentaries", ignore = true)
    Deck toDomain(DeckRequestDTO deckRequestDTO);
}