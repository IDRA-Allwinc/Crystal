package com.crystal.model.entities.catalog;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="meeting_type")
public class MeetingType {

    @Id
    @GeneratedValue
    @Column(name = "id_meeting_type")
    private Long id;

    @Column(name="name", unique = true, length = 200, nullable = false)
    @NotEmpty(message="El nombre es un campo requerido")
    private String name;

    @Column(name="description", length = 200, nullable = false)
    @NotEmpty(message="La descripción es un campo requerido")
    private String description;

    @Column(name="is_obsolete", nullable = false)
    private boolean isObsolete;

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

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }
}
