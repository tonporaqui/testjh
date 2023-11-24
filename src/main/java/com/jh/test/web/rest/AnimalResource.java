package com.jh.test.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jh.test.service.AnimalService;
import com.jh.test.service.dto.AnimalDTO;
import com.jh.test.web.rest.errors.BadRequestAlertException;

import jakarta.validation.Valid;

/**
 * REST controller for managing {@link com.jh.test.domain.Animal}.
 */
@RestController
@RequestMapping("/api/animals")
public class AnimalResource {

    private final AnimalService animalService;
    private static final Logger log = LoggerFactory.getLogger(AnimalResource.class);
    private static final String ENTITY_NAME = "testjhAnimal";

    public AnimalResource(AnimalService animalService) {
        this.animalService = animalService;
    }

    /**
     * {@code POST  /animals} : Create a new animal.
     *
     * @param animalDTO the animalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new animalDTO, or with status {@code 400 (Bad Request)} if
     *         the animal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnimalDTO> crearAnimal(@Valid @RequestBody AnimalDTO animalDTO) throws URISyntaxException {
        log.debug("REST request to save Animal : {}", animalDTO);
        if (animalDTO.getId() != null) {
            throw new BadRequestAlertException("A new animal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalDTO result = animalService.guardarAnimal(animalDTO);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating animal");
        }
        return ResponseEntity.created(new URI("/api/animals/" + result.getId()))
                .body(result);
    }

    /**
     * {@code GET  /animals/:id} : Retrieves the animal with the specified ID.
     *
     * @param id the ID of the animal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the animalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> obtenerAnimal(@PathVariable String id) {
        log.debug("REST request to get Animal : {}", id);
        return animalService.buscarPorId(id)
                .map(animalDTO -> {
                    log.info("Animal found with id: {}", id);
                    return ResponseEntity.ok(animalDTO);
                })
                .orElseGet(() -> {
                    log.warn("Animal with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * {@code PUT  /animals/:id} : Update an animal with the specified ID.
     *
     * @param id        the ID of the animal to be updated.
     * @param animalDTO the updated animal data.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated animalDTO, or with status {@code 404 (Not Found)} if the
     *         animal ID does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnimalDTO> actualizarAnimal(@PathVariable String id,
            @Valid @RequestBody AnimalDTO animalDTO) {
        log.debug("REST request to update Animal with id: {}: {}", id, animalDTO);
        if (!animalService.existsById(id)) {
            log.warn("Attempted to update non-existing animal with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        animalDTO.setId(id);
        AnimalDTO resultado = animalService.actualizarAnimal(animalDTO);
        log.info("Updated Animal: {}", resultado);
        return ResponseEntity.ok().body(resultado);
    }

    /**
     * {@code DELETE  /animals/:id} : Deletes an animal by its ID.
     *
     * @param id the ID of the animal to be deleted.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAnimal(@PathVariable String id) {
        log.debug("REST request to delete Animal with id: {}", id);
        if (!animalService.existsById(id)) {
            log.warn("Attempted to delete non-existing animal with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        animalService.eliminarAnimal(id);
        log.info("Deleted Animal with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * {@code GET /animals/especie/{especie}} : Retrieves a list of animals based on
     * the specified species.
     *
     * @param especie the species of the animals to retrieve.
     * @return a list of {@link AnimalDTO} objects matching the specified species.
     */
    @GetMapping("/especie/{especie}")
    public List<AnimalDTO> obtenerAnimalesPorEspecie(@PathVariable String especie) {
        log.debug("REST request to get Animals by species: {}", especie);
        List<AnimalDTO> animales = animalService.buscarPorEspecie(especie);
        if (animales.isEmpty()) {
            log.info("No animals found for species: {}", especie);
        } else {
            log.info("Found {} animals for species: {}", animales.size(), especie);
        }
        return animales;
    }

    /**
     * {@code GET /animals/nombre/{nombre}} : Retrieves a list of animals based on
     * the given name.
     *
     * @param nombre the name of the animals to retrieve.
     * @return a list of {@link AnimalDTO} objects with the specified name.
     */
    @GetMapping("/nombre/{nombre}")
    public List<AnimalDTO> obtenerAnimalesPorNombre(@PathVariable String nombre) {
        log.debug("REST request to get Animals by name: {}", nombre);
        List<AnimalDTO> animales = animalService.buscarPorNombre(nombre);
        if (animales.isEmpty()) {
            log.info("No animals found with name: {}", nombre);
        } else {
            log.info("Found {} animals with name: {}", animales.size(), nombre);
        }
        return animales;
    }

    /**
     * {@code GET  /animals/buscar} : Retrieves a list of animals based on the
     * provided search criteria.
     *
     * @param especie the species of the animal (optional).
     * @param nombre  the name of the animal (optional).
     * @return a list of {@link AnimalDTO} objects that match the search criteria.
     */
    @GetMapping("/buscar")
    public List<AnimalDTO> buscarAnimales(@RequestParam(required = false) String especie,
            @RequestParam(required = false) String nombre) {
        List<AnimalDTO> animales;
        if (especie != null && nombre != null) {
            log.debug("Searching for animals by species '{}' and name '{}'", especie, nombre);
            animales = animalService.buscarPorEspecieYNombre(especie, nombre);
        } else if (especie != null) {
            log.debug("Searching for animals by species '{}'", especie);
            animales = animalService.buscarPorEspecie(especie);
        } else if (nombre != null) {
            log.debug("Searching for animals by name '{}'", nombre);
            animales = animalService.buscarPorNombre(nombre);
        } else {
            log.debug("Listing all animals");
            animales = animalService.listarTodos();
        }

        if (animales.isEmpty()) {
            log.info("No animals found for the given search criteria");
        } else {
            log.info("Found {} animals for the given search criteria", animales.size());
        }

        return animales;
    }

    /**
     * {@code GET  /animals} : Retrieves a list of all animals.
     *
     * @return A list of {@link AnimalDTO} objects representing all animals.
     */
    @GetMapping("")
    public List<AnimalDTO> listarTodosLosAnimales() {
        log.debug("REST request to get all Animals");
        List<AnimalDTO> animales = animalService.listarTodos();
        if (animales.isEmpty()) {
            log.info("No animals found in the database");
        } else {
            log.info("Found {} animals in the database", animales.size());
        }
        return animales;
    }

}
