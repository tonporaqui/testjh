package com.jh.test.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
// import lombok.Data;

@Document(collection = "animales")
// @Data // Esta anotación incluye getters, setters, equals, hashCode y toString
public class Animal {

    @Id
    private String id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Length(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    @NotBlank(message = "La especie no puede estar vacía")
    @Length(max = 100, message = "La especie no puede tener más de 100 caracteres")
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
