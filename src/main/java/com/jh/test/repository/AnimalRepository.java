package com.jh.test.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.test.domain.Animal;

public interface AnimalRepository extends MongoRepository<Animal, String> {
    List<Animal> findByEspecie(String especie);

    List<Animal> findByNombre(String nombre);

    List<Animal> findByEspecieAndNombre(String especie, String nombre);
}
