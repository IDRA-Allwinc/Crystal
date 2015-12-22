package com.crystal.model.entities.catalog.view;

import com.crystal.model.shared.EntityGrid;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select id_audited_entity, audited_entity.name, responsible, email, phone, audited_entity_type.name as audited_entity_type_name " +
        "from audited_entity " +
        "inner join audited_entity_type on audited_entity.id_audited_entity_type = audited_entity_type.id_audited_entity_type " +
        "where audited_entity.is_obsolete=false")
public class AuditedEntityView implements EntityGrid {
    @Id
    @Column(name = "id_audited_entity")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "responsible")
    private String responsible;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "audited_entity_type_name")
    private String auditedEntityTypeName;

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

    public String getAuditedEntityTypeName() {
        return auditedEntityTypeName;
    }

    public void setAuditedEntityTypeName(String auditedEntityTypeName) {
        this.auditedEntityTypeName = auditedEntityTypeName;
    }
}
