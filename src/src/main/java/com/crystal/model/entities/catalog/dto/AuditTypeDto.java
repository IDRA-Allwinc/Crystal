package com.crystal.model.entities.catalog.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class AuditTypeDto {

    private Long id;

    @NotEmpty(message="El nombre es un campo requerido")
    @Length(min = 8, max  = 200, message = "El nombre debe tener entre 8 y 200 caracteres.")
    private String name;

    @NotEmpty(message="La descripción es un campo requerido")
    @Length(min = 8, max  = 200, message = "La descripción debe tener entre 8 y 200 caracteres.")
    private String description ;

    public AuditTypeDto() {

    }

    public AuditTypeDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
