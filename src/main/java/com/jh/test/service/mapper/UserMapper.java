package com.jh.test.service.mapper;

import com.jh.test.domain.Perfil;
import com.jh.test.domain.User;
import com.jh.test.service.dto.PerfilDTO;
import com.jh.test.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
    @Mapping(target = "perfil", source = "perfil", qualifiedByName = "perfilId")
    UserDTO toDto(User s);

    @Named("perfilId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PerfilDTO toDtoPerfilId(Perfil perfil);
}
