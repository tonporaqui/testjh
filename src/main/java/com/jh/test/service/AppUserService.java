package com.jh.test.service;

import com.jh.test.domain.AppUser;
import com.jh.test.domain.Perfil;
import com.jh.test.repository.AppUserRepository;
import com.jh.test.repository.PerfilRepository;
import com.jh.test.service.dto.AppUserDTO;
import com.jh.test.service.mapper.AppUserMapper;
import com.jh.test.web.rest.errors.BadRequestAlertException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jh.test.domain.AppUser}.
 */
@Service
@Transactional
public class AppUserService {

    private final Logger log = LoggerFactory.getLogger(AppUserService.class);

    private final AppUserRepository appUserRepository;

    private final AppUserMapper appUserMapper;

    private final PerfilRepository perfilRepository;

    public AppUserService(AppUserRepository appUserRepository, AppUserMapper appUserMapper, PerfilRepository perfilRepository) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
        this.perfilRepository = perfilRepository;
    }

    /**
     * Save a appUser.
     *
     * @param appUserDTO the entity to save.
     * @return the persisted entity.
     */
    public AppUserDTO save(AppUserDTO appUserDTO) {
        log.debug("Request to save AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);
        // Cargar y asignar el Perfil si es necesario
        if (appUserDTO.getPerfil() != null && appUserDTO.getPerfil().getId() != null) {
            Perfil perfil = perfilRepository
                .findById(appUserDTO.getPerfil().getId())
                .orElseThrow(() -> new BadRequestAlertException("Perfil no encontrado", "perfil", "idnotfound"));
            appUser.setPerfil(perfil);
        }
        appUser = appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }

    /**
     * Update a appUser.
     *
     * @param appUserDTO the entity to save.
     * @return the persisted entity.
     */
    public AppUserDTO update(AppUserDTO appUserDTO) {
        log.debug("Request to update AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);

        // Cargar y asignar el Perfil si es necesario
        if (appUserDTO.getPerfil() != null && appUserDTO.getPerfil().getId() != null) {
            Perfil perfil = perfilRepository
                .findById(appUserDTO.getPerfil().getId())
                .orElseThrow(() -> new BadRequestAlertException("Perfil no encontrado", "perfil", "idnotfound"));
            appUser.setPerfil(perfil);
        }

        appUser = appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }

    /**
     * Partially update a appUser.
     *
     * @param appUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppUserDTO> partialUpdate(AppUserDTO appUserDTO) {
        log.debug("Request to partially update AppUser : {}", appUserDTO);

        return appUserRepository
            .findById(appUserDTO.getId())
            .map(existingAppUser -> {
                appUserMapper.partialUpdate(existingAppUser, appUserDTO);

                // Si el DTO contiene un perfil y tiene un ID, se asocia con el usuario
                if (appUserDTO.getPerfil() != null && appUserDTO.getPerfil().getId() != null) {
                    Perfil perfil = perfilRepository
                        .findById(appUserDTO.getPerfil().getId())
                        .orElseThrow(() -> new BadRequestAlertException("Perfil no encontrado", "perfil", "idnotfound"));
                    existingAppUser.setPerfil(perfil);
                }

                return existingAppUser;
            })
            .map(appUserRepository::save)
            .map(appUserMapper::toDto);
    }

    /**
     * Get all the appUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppUsers");
        return appUserRepository.findAll(pageable).map(appUserMapper::toDto);
    }

    /**
     * Get one appUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppUserDTO> findOne(Long id) {
        log.debug("Request to get AppUser : {}", id);
        return appUserRepository.findById(id).map(appUserMapper::toDto);
    }

    /**
     * Delete the appUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppUser : {}", id);
        appUserRepository.deleteById(id);
    }
}
