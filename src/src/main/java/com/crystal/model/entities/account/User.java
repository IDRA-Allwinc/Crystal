package com.crystal.model.entities.account;

import com.crystal.infrastructure.security.CryptoRfc2898;
import com.crystal.model.entities.catalog.AuditedEntity;
import com.crystal.model.shared.Constants;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_user", nullable = false)
    private Long id;

    @Column(name="username", unique = true, length = 200, nullable = false)
    @NotEmpty(message="El usuario es un campo requerido.")
    private String username;

    @Column(name="password", length = 1000, nullable = false)
    @NotEmpty(message="Contraseña es un campo requerido.")
    private String password;

    @Column(name="fullName", length = 500, nullable = false)
    @NotEmpty(message="Nombre completo es un campo requerido.")
    private String fullName;

    @Column(name="email", length = 1000, nullable = false)
    @NotEmpty(message="Correo electrónico es un campo requerido.")
    private String email;

    @Column(name="enabled", nullable = false)
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_role",nullable = false)
    private Role role;

    //es nullable por que el director y el administrador no estaran asociados a ningun ente fiscalizado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_audited_entity",nullable = true)
    private AuditedEntity auditedEntity;

    public User(){
    }

    public User(Long id){
        this.id = id;
    }

    public User(Long id, Boolean enabled){
        this.id = id;
        this.enabled = enabled;
    }

    public User(Long id, String username){
        this.id = id;
        this.username = username;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public AuditedEntity getAuditedEntity() {
        return auditedEntity;
    }

    public void setAuditedEntity(AuditedEntity auditedEntity) {
        this.auditedEntity = auditedEntity;
    }

    public void merge(UserDto modelNew) {
        if(modelNew.getId() == null){
            mergePassword(modelNew.getPsw().getPassword());
            enabled = true;
        }

        username = modelNew.getUsername();
        fullName = modelNew.getFullName();
        email = modelNew.getEmail();
        role = new Role();
        role.setId(modelNew.getRoleId());

        if(modelNew.getRoleCode().equals(Constants.ROLE_LINK)){
             auditedEntity = new AuditedEntity();
             auditedEntity.setId(modelNew.getAuditedEntityId());
        }else{
            auditedEntity = null;
        }

    }

    public void mergePassword(String passwordNew) {
        CryptoRfc2898 cryptoRfc2898 = new CryptoRfc2898();
        password = cryptoRfc2898.encode(passwordNew);
    }
}
