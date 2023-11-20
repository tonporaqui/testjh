package com.jh.test.service.mapper;

import com.jh.test.domain.AppUser;
import com.jh.test.domain.Perfil;
import com.jh.test.service.dto.AppUserDTO;
import com.jh.test.service.dto.PerfilDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "perfil", source = "perfil", qualifiedByName = "perfilId")
    AppUserDTO toDto(AppUser s);

    @Named("perfilId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PerfilDTO toDtoPerfilId(Perfil perfil);
}
