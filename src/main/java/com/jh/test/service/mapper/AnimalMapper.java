package com.jh.test.service.mapper;

import com.jh.test.domain.Animal;
import com.jh.test.service.dto.AnimalDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnimalMapper {
    Animal toEntity(AnimalDTO animalDTO);

    AnimalDTO toDto(Animal animal);

    // Si tienes listas o sets
    List<AnimalDTO> toDto(List<Animal> animals);

    List<Animal> toEntity(List<AnimalDTO> animalDTOs);
}
