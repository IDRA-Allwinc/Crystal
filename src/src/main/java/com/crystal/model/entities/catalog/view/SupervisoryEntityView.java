package com.crystal.model.entities.catalog.view;

import com.crystal.model.shared.EntityGrid;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select id_supervisory_entity, name, responsible, email, phone, belongs_to " +
        "from supervisory_entity " +
        "where supervisory_entity.is_obsolete=false")
public class SupervisoryEntityView implements EntityGrid {
    @Id
    @Column(name = "id_supervisory_entity")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "responsible")
    private String responsible;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Override
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

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }
}
