package com.crystal.model.entities.account;

import javax.persistence.*;

/**
 * Created by Developer on 09/12/2015.
 */

@Entity
@Table(name="role")
public class Role {

    //se omiten las validaciones, el catalogo no se gestiona por pantallas jsp
    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long id;

    @Column(name="name", unique = true, length = 200, nullable = false)
    private String name;

    @Column(name="description", length = 200, nullable = false)
    private String description;

    @Column(name="code", unique = true, length = 200, nullable = false)
    private String code;

    @Column(name="is_obsolete", nullable = false)
    private boolean isObsolete;

    public Role() {
    }

    public Role(Long roleId) {
        id = roleId;
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
