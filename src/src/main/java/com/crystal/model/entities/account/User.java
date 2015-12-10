package com.crystal.model.entities.account;

import com.crystal.model.entities.catalog.AuditedEntity;
import com.crystal.model.entities.shared.EntityGrid;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User implements EntityGrid {

    @Id
    @GeneratedValue
    @Column(name="id_user")
    private Long id;

    @Column(name="username", unique = true, length = 200, nullable = false)
    @NotEmpty(message="El usuario es un campo requerido")
    private String username;

    @Column(name="password", length = 1000, nullable = false)
    @NotEmpty(message="Contraseña es un campo requerido")
    private String password;

    @Transient
    @NotEmpty(message="Confirmación es un campo requerido")
    private String confirm;

    @Column(name="fullname", length = 500, nullable = false)
    @NotEmpty(message="Nombre completo es un campo requerido")
    private String fullname;

    @Column(name="email", length = 1000, nullable = false)
    @NotEmpty(message="Correo electrónico es un campo requerido")
    private String email;

    @Column(name="enabled", nullable = false)
    private Boolean enabled;

    @Transient
    private Boolean hasChangePass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_role",nullable = false)
    private Role role;

    //es nullable por que el director y el administrador no estaran asociados a ningun ente fiscalizado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_audited_entity",nullable = true)
    private AuditedEntity auditedEntity;

    /*
    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RelMessageUserReceiver> messageUserReceivers;

    @OneToMany(mappedBy="sender", cascade={CascadeType.ALL})
    private List<Message> messagesSent;
    */

    public User(){

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

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getHasChangePass() {
        return hasChangePass;
    }

    public void setHasChangePass(Boolean hasChangePass) {
        this.hasChangePass = hasChangePass;
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
    /*
    public List<RelMessageUserReceiver> getMessageUserReceivers() {
        return messageUserReceivers;
    }

    public void setMessageUserReceivers(List<RelMessageUserReceiver> messageUserReceivers) {
        this.messageUserReceivers = messageUserReceivers;
    }

    public List<Message> getMessagesSent() {
        return messagesSent;
    }

    public void setMessagesSent(List<Message> messagesSent) {
        this.messagesSent = messagesSent;
    }  */
}