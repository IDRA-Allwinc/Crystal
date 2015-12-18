package com.crystal.model.entities.catalog.dto;

public class AuditedEntityDto {


    private Long id;

    private String name;

    private String responsible ;

    private String phone;

    private String email;

    private Long auditedEntityTypeId;

    public AuditedEntityDto(){

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
