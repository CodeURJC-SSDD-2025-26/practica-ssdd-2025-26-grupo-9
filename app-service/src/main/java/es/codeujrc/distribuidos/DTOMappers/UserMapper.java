package es.codeujrc.distribuidos.DTOMappers;

import es.codeujrc.distribuidos.DTOs.UserBasicResponseDTO;
import es.codeujrc.distribuidos.DTOs.UserRequestDTO;
import es.codeujrc.distribuidos.DTOs.UserResponseDTO;
import es.codeujrc.distribuidos.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toResponseDTO(User user);
    UserBasicResponseDTO toBasicResponseDTO(User user);
    List<UserResponseDTO> toResponseDTOs(Collection<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "decks", ignore = true)
    @Mapping(target = "commentaries", ignore = true)
    @Mapping(target = "following", ignore = true)
    @Mapping(target = "followers", ignore = true)
    User toDomain(UserRequestDTO userRequestDTO);
}