package com.crystal.model.entities.catalog.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class MeetingTypeDto {

    private Long id;

    @NotEmpty(message="El tipo de reunión es un campo requerido")
    @Size(max = 200, message="El tipo de reunión debe tener 200 caracteres máximo")
    private String name;

    @NotEmpty(message="La descripción es un campo requerido")
    @Size(max = 300, message="La descripción debe tener 300 caracteres máximo")
    private String description;

    public MeetingTypeDto() {
    }

    public MeetingTypeDto(Long id, String name, String description) {
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
