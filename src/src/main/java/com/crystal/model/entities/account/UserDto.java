package com.crystal.model.entities.account;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserDto {

    private Long id;

    @NotEmpty(message = "El usuario es un campo requerido")
    private String username;

    @NotEmpty(message = "La contraseña es un campo requerido")
    private String password;

    @NotEmpty(message = "La confirmación es un campo requerido")
    private String confirm;

    @NotEmpty(message = "El nombre completo es un campo requerido")
    private String fullName;

    @NotEmpty(message = "El correo es requerido")
    @Email(message = "El correo electrónico no es válido")
    private String email;

    @NotNull(message = "El perfil es un campo requerido")
    private Long roleId;

    private Long auditedEntityId;

    public UserDto(){

    }

    public UserDto(Long id, String username, String fullName, String email, Long roleId, Long auditedEntityId) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.roleId = roleId;
        this.auditedEntityId = auditedEntityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAuditedEntityId() {
        return auditedEntityId;
    }

    public void setAuditedEntityId(Long auditedEntityId) {
        this.auditedEntityId = auditedEntityId;
    }
}
