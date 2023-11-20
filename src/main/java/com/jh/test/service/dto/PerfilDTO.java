package com.jh.test.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jh.test.domain.Perfil} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilDTO)) {
            return false;
        }

        PerfilDTO perfilDTO = (PerfilDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, perfilDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
