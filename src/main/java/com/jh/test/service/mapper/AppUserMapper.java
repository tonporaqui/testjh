package com.jh.test.service.mapper;

import com.jh.test.domain.AppUser;
import com.jh.test.domain.Perfil;
import com.jh.test.service.dto.AppUserDTO;
import com.jh.test.service.dto.PerfilDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "perfil", source = "perfil")
    AppUserDTO toDto(AppUser appUser);

    @Mapping(target = "perfil", source = "perfil")
    AppUser toEntity(AppUserDTO appUserDTO);

    // Este método podría no ser necesario si no necesitas convertir solo el ID de
    // Perfil a PerfilDTO
    @Named("perfilId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PerfilDTO toDtoPerfilId(Perfil perfil);

    default AppUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppUser appUser = new AppUser();
        appUser.setId(id);
        return appUser;
    }
}
