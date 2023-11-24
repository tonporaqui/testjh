package com.jh.test.web.rest;

import com.jh.test.service.AnimalService;
import com.jh.test.service.dto.AnimalDTO;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/animales")
public class AnimalResource {

    private final AnimalService animalService;

    public AnimalResource(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping("")
    public ResponseEntity<AnimalDTO> crearAnimal(@RequestBody AnimalDTO animalDTO) throws URISyntaxException {
        AnimalDTO resultado = animalService.guardarAnimal(animalDTO);
        return ResponseEntity.created(new URI("/api/animales/" + resultado.getId())).body(resultado);
    }
    // Otros endpoints
}
