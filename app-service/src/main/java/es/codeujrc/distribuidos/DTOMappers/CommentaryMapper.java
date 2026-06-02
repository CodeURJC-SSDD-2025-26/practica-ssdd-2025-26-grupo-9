package es.codeujrc.distribuidos.DTOMappers;

import es.codeujrc.distribuidos.DTOs.CommentaryRequestDTO;
import es.codeujrc.distribuidos.DTOs.CommentaryResponseDTO;
import es.codeujrc.distribuidos.entity.Commentary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentaryMapper {

    CommentaryResponseDTO toResponseDTO(Commentary commentary);
    List<CommentaryResponseDTO> toResponseDTOs(Collection<Commentary> commentaries);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deck", ignore = true)
    @Mapping(target = "user", ignore = true)
    Commentary toDomain(CommentaryRequestDTO commentaryRequestDTO);
}