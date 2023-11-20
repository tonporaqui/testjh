package com.jh.test.service.mapper;

import com.jh.test.domain.Perfil;
import com.jh.test.service.dto.PerfilDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Perfil} and its DTO {@link PerfilDTO}.
 */
@Mapper(componentModel = "spring")
public interface PerfilMapper extends EntityMapper<PerfilDTO, Perfil> {}
