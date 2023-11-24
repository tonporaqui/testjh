package com.jh.test.service;

import com.jh.test.domain.Animal;
import com.jh.test.repository.AnimalRepository;
import com.jh.test.service.dto.AnimalDTO;
import com.jh.test.service.mapper.AnimalMapper;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    public AnimalService(AnimalRepository animalRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    public AnimalDTO guardarAnimal(AnimalDTO animalDTO) {
        Animal animal = animalMapper.toEntity(animalDTO);
        animal = animalRepository.save(animal);
        return animalMapper.toDto(animal);
    }
    // Otros métodos
}
