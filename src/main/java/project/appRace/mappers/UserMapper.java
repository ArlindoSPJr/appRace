package project.appRace.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.appRace.dto.user.UserCreateDto;
import project.appRace.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) 
    User toEntity(UserCreateDto dto);
}