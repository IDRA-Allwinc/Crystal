package com.crystal.model.entities.catalog;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="audited_entity")
public class AuditedEntity {

    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_audited_entity")
    private Long id;

    @Column(name="name", length = 200, nullable = false)
    @NotEmpty(message="El nombre es un campo requerido")
    private String name;

    @Column(name="responsible", length = 200, nullable = false)
    @NotEmpty(message="El nombre del responsable es un campo requerido")
    private String responsible ;

    @Column(name="phone", length = 200, nullable = false)
    @NotEmpty(message="El teléfono es un campo requerido")
    private String phone;

    @Column(name="email", length = 200, nullable = false)
    @NotEmpty(message="El email es un campo requerido")
    private String email;

    @Column(name="is_obsolete", nullable = false)
    private boolean isObsolete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_audited_entity_type", nullable = false)
    private AuditedEntityType auditedEntityType;

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

    public AuditedEntityType getAuditedEntityType() {
        return auditedEntityType;
    }

    public void setAuditedEntityType(AuditedEntityType auditedEntityType) {
        this.auditedEntityType = auditedEntityType;
    }
}
