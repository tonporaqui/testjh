package com.jh.test.repository;

import com.jh.test.domain.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimalRepository extends MongoRepository<Animal, String> {}
