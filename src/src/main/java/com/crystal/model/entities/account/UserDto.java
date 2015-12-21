package com.crystal.model.entities.account;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDto {

    private Long id;

    @NotEmpty(message = "El usuario es un campo requerido.")
    @Length(min = 8, max  = 200, message = "El usuario debe tener entre 6 y 200 caracteres.")
    @Pattern(regexp = "\\w*", flags = Pattern.Flag.CASE_INSENSITIVE, message = "El usuario no puede contener caracteres especiales, sólo valores alfanuméricos.")
    private String username;

    @NotEmpty(message = "El nombre completo es un campo requerido.")
    @Length(min = 3, max  = 500, message = "El nombre completo debe tener entre 3 y 500 caracteres.")
    private String fullName;

    @NotEmpty(message = "El correo es requerido.")
    @Email(message = "El correo electrónico no es válido.")
    private String email;

    @NotNull(message = "El perfil es un campo requerido.")
    private Long roleId;

    private String roleCode;

    private Long auditedEntityId;

    @Valid
    private PasswordDto psw;

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

    public PasswordDto getPsw() {
        return psw;
    }

    public void setPsw(PasswordDto psw) {
        this.psw = psw;
    }


    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}


