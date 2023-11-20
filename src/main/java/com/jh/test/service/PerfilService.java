package com.jh.test.service;

import com.jh.test.domain.Perfil;
import com.jh.test.repository.PerfilRepository;
import com.jh.test.service.dto.PerfilDTO;
import com.jh.test.service.mapper.PerfilMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jh.test.domain.Perfil}.
 */
@Service
@Transactional
public class PerfilService {

    private final Logger log = LoggerFactory.getLogger(PerfilService.class);

    private final PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    public PerfilService(PerfilRepository perfilRepository, PerfilMapper perfilMapper) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
    }

    /**
     * Save a perfil.
     *
     * @param perfilDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilDTO save(PerfilDTO perfilDTO) {
        log.debug("Request to save Perfil : {}", perfilDTO);
        Perfil perfil = perfilMapper.toEntity(perfilDTO);
        perfil = perfilRepository.save(perfil);
        return perfilMapper.toDto(perfil);
    }

    /**
     * Update a perfil.
     *
     * @param perfilDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilDTO update(PerfilDTO perfilDTO) {
        log.debug("Request to update Perfil : {}", perfilDTO);
        Perfil perfil = perfilMapper.toEntity(perfilDTO);
        perfil = perfilRepository.save(perfil);
        return perfilMapper.toDto(perfil);
    }

    /**
     * Partially update a perfil.
     *
     * @param perfilDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerfilDTO> partialUpdate(PerfilDTO perfilDTO) {
        log.debug("Request to partially update Perfil : {}", perfilDTO);

        return perfilRepository
            .findById(perfilDTO.getId())
            .map(existingPerfil -> {
                perfilMapper.partialUpdate(existingPerfil, perfilDTO);

                return existingPerfil;
            })
            .map(perfilRepository::save)
            .map(perfilMapper::toDto);
    }

    /**
     * Get all the perfils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Perfils");
        return perfilRepository.findAll(pageable).map(perfilMapper::toDto);
    }

    /**
     *  Get all the perfils where User is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PerfilDTO> findAllWhereUserIsNull() {
        log.debug("Request to get all perfils where User is null");
        return StreamSupport
            .stream(perfilRepository.findAll().spliterator(), false)
            .filter(perfil -> perfil.getUser() == null)
            .map(perfilMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the perfils where AppUser is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PerfilDTO> findAllWhereAppUserIsNull() {
        log.debug("Request to get all perfils where AppUser is null");
        return StreamSupport
            .stream(perfilRepository.findAll().spliterator(), false)
            .filter(perfil -> perfil.getAppUser() == null)
            .map(perfilMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one perfil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilDTO> findOne(Long id) {
        log.debug("Request to get Perfil : {}", id);
        return perfilRepository.findById(id).map(perfilMapper::toDto);
    }

    /**
     * Delete the perfil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Perfil : {}", id);
        perfilRepository.deleteById(id);
    }
}
