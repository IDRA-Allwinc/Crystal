package com.crystal.model.entities.catalog.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class AuditedEntityDto {

    private Long id;

    @NotEmpty(message="El nombre es un campo requerido")
    @Length(min = 8, max  = 200, message = "El nombre debe tener entre 6 y 200 caracteres.")
    private String name;

    @NotEmpty(message="El nombre del responsable es un campo requerido")
    @Length(min = 8, max  = 200, message = "El nombre del responsable debe tener entre 6 y 200 caracteres.")
    private String responsible ;

    @NotEmpty(message="El teléfono es un campo requerido")
    @Length(min = 8, max  = 200, message = "El teléfono debe tener entre 6 y 200 caracteres.")
    private String phone;

    @NotEmpty(message="El email es un campo requerido")
    @Email(message = "El correo electrónico no es válido")
    private String email;

    @NotNull(message = "El tipo de entidad fiscalizada es un campo requerido.")
    private Long auditedEntityTypeId;

    public AuditedEntityDto(){

    }

    public AuditedEntityDto(Long id, String name, String responsible, String phone, String email, Long auditedEntityTypeId) {
        this.id = id;
        this.name = name;
        this.responsible = responsible;
        this.phone = phone;
        this.email = email;
        this.auditedEntityTypeId = auditedEntityTypeId;
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

    public Long getAuditedEntityTypeId() {
        return auditedEntityTypeId;
    }

    public void setAuditedEntityTypeId(Long auditedEntityTypeId) {
        this.auditedEntityTypeId = auditedEntityTypeId;
    }
}
