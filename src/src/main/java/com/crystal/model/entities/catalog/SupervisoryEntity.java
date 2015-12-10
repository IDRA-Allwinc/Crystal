package com.crystal.model.entities.catalog;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="supervisory_entity")
public class SupervisoryEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_supervisory_entity")
    private Long id;

    @Column(name="name", unique = true, length = 200, nullable = false)
    @NotEmpty(message="El nombre es un campo requerido")
    private String name;

    @Column(name="belongs_to", unique = true, length = 200, nullable = false)
    @NotEmpty(message="A quien pertenece es un campo requerido")
    private String belongsTo;

    @Column(name="responsible", unique = true, length = 200, nullable = false)
    @NotEmpty(message="El nombre del responsable es un campo requerido")
    private String responsible ;

    @Column(name="phone", unique = true, length = 200, nullable = false)
    @NotEmpty(message="El teléfono es un campo requerido")
    private String phone;

    @Column(name="email", unique = true, length = 200, nullable = false)
    @NotEmpty(message="El email es un campo requerido")
    private String email;

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

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }
}