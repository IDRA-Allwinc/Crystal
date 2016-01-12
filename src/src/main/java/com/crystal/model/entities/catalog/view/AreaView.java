package com.crystal.model.entities.catalog.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select a.id_area, ae.id_audited_entity, a.name, a.responsible, a.email, a.phone, a.is_obsolete, ae.name as audited_entity_name, aet.code " +
        "from area a " +
        "inner join audited_entity ae on ae.id_audited_entity = a.id_audited_entity "+
        "inner join audited_entity_type aet on ae.id_audited_entity_type = aet.id_audited_entity_type")
public class AreaView  {
    @Id
    @Column(name = "id_area")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "responsible")
    private String responsible;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_obsolete")
    private boolean isObsolete;

    @Column(name = "id_audited_entity")
    private String auditedEntityId;

    @Column(name = "code")
    private String auditedEntityTypeCode;

    @Column(name = "audited_entity_name")
    private String auditedEntityName;

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

    public String getAuditedEntityName() {
        return auditedEntityName;
    }

    public void setAuditedEntityName(String auditedEntityName) {
        this.auditedEntityName = auditedEntityName;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public String getAuditedEntityId() {
        return auditedEntityId;
    }

    public void setAuditedEntityId(String auditedEntityId) {
        this.auditedEntityId = auditedEntityId;
    }

    public String getAuditedEntityTypeCode() {
        return auditedEntityTypeCode;
    }

    public void setAuditedEntityTypeCode(String auditedEntityTypeCode) {
        this.auditedEntityTypeCode = auditedEntityTypeCode;
    }
}
