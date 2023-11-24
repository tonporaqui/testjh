package com.jh.test.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
// import lombok.Data;

// @Data // Esta anotación incluye getters, setters, equals, hashCode y toString
public class AnimalDTO {

    @Schema(description = "Identificador único del animal")
    private String id;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Nombre del animal")
    private String nombre;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Especie del animal")
    private String especie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

}
