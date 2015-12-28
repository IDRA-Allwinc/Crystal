package com.crystal.model.entities.catalog;

import javax.persistence.*;

@Entity
@Table(name="event_type")
public class ObservationType {

    @Id
    @GeneratedValue
    @Column(name = "id_observation_type")
    private Long id;

    @Column(name="name", length = 200, nullable = false)
    private String name;

    @Column(name="code", length = 200, nullable = false)
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

}
