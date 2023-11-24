package com.jh.test.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.jh.test.domain.Animal;
import com.jh.test.service.dto.AnimalDTO;

/**
 * Mapper for the entity {@link Animal} and its DTO {@link AnimalDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnimalMapper extends EntityMapper<AnimalDTO, Animal> {
    Animal toEntity(AnimalDTO animalDTO);

    AnimalDTO toDto(Animal animal);

    // Si tienes listas o sets
    List<AnimalDTO> toDto(List<Animal> animals);

    List<Animal> toEntity(List<AnimalDTO> animalDTOs);
}
