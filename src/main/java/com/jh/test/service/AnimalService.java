package com.jh.test.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jh.test.domain.Animal;
import com.jh.test.repository.AnimalRepository;
import com.jh.test.service.dto.AnimalDTO;
import com.jh.test.service.mapper.AnimalMapper;

/**
 * Service Implementation for managing {@link com.jh.test.domain.Animal}.
 */
@Service
@Transactional
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final Logger log = LoggerFactory.getLogger(AppUserService.class);

    public AnimalService(AnimalRepository animalRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    /**
     * Save an animal.
     *
     * @param animalDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalDTO guardarAnimal(AnimalDTO animalDTO) {
        log.debug("Request to save Animal : {}", animalDTO);
        Animal animal = animalMapper.toEntity(animalDTO);
        animal = animalRepository.save(animal);
        return animalMapper.toDto(animal);
    }

    /**
     * Retrieves a list of AnimalDTO objects based on the specified species.
     *
     * @param especie the species of animals to search for
     * @return a list of AnimalDTO objects matching the specified species
     */
    @Transactional(readOnly = true)
    public List<AnimalDTO> buscarPorEspecie(String especie) {
        log.debug("Request to get Animals by species : {}", especie);
        List<AnimalDTO> result = animalRepository.findByEspecie(especie).stream()
                .map(animalMapper::toDto)
                .collect(Collectors.toList());
        log.info("Found {} animals for species {}", result.size(), especie);
        return result;
    }

    /**
     * Retrieves a list of AnimalDTO objects by searching for animals with the given
     * name.
     *
     * @param nombre the name to search for
     * @return a list of AnimalDTO objects
     */
    @Transactional(readOnly = true)
    public List<AnimalDTO> buscarPorNombre(String nombre) {
        log.debug("Request to get Animals by name : {}", nombre);
        List<AnimalDTO> result = animalRepository.findByNombre(nombre).stream()
                .map(animalMapper::toDto)
                .collect(Collectors.toList());
        log.info("Found {} animals with name {}", result.size(), nombre);
        return result;
    }

    /**
     * Retrieves a list of AnimalDTO objects based on the given species and name.
     *
     * @param especie the species of the animal
     * @param nombre  the name of the animal
     * @return a list of AnimalDTO objects matching the specified species and name
     */
    @Transactional(readOnly = true)
    public List<AnimalDTO> buscarPorEspecieYNombre(String especie, String nombre) {
        log.debug("Request to get Animals by species '{}' and name '{}'", especie, nombre);
        List<AnimalDTO> result = animalRepository.findByEspecieAndNombre(especie, nombre).stream()
                .map(animalMapper::toDto)
                .collect(Collectors.toList());
        log.info("Found {} animals for species '{}' and name '{}'", result.size(), especie, nombre);
        return result;
    }

    /**
     * Get one animal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalDTO> buscarPorId(String id) {
        log.debug("Request to get Animal : {}", id);
        return animalRepository.findById(id)
                .map(animalMapper::toDto);
    }

    /**
     * Delete the animal by id.
     *
     * @param id the id of the entity.
     */
    public void eliminarAnimal(String id) {
        log.debug("Request to delete Animal : {}", id);
        animalRepository.deleteById(id);
    }

    /**
     * Update an animal.
     *
     * @param animalDTO the entity to update.
     * @return the updated entity.
     */
    public AnimalDTO actualizarAnimal(AnimalDTO animalDTO) {
        log.debug("Request to update Animal : {}", animalDTO);
        Animal animal = animalMapper.toEntity(animalDTO);
        animal = animalRepository.save(animal);
        return animalMapper.toDto(animal);
    }

    /**
     * Get all the animals.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalDTO> listarTodos() {
        log.debug("Request to get all Animals");
        return animalRepository.findAll().stream()
                .map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Check if an animal with the given id exists.
     *
     * @param id the id of the entity.
     * @return boolean indicating existence of the entity.
     */
    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        log.debug("Request to check if Animal exists : {}", id);
        return animalRepository.existsById(id);
    }

}
