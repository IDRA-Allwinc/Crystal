package com.crystal.model.entities.catalog;

import com.crystal.model.entities.catalog.dto.MeetingTypeDto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="meeting_type")
public class MeetingType {

    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_meeting_type")
    private Long id;

    @Column(name="name", length = 300, nullable = false)
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

    public void merge(MeetingTypeDto modelNew) {
        name = modelNew.getName();
        description = modelNew.getDescription();
    }
}
