package com.crystal.model.entities.audit;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="notification")
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "id_notification")
    private Long id;

    @Column(name="title", length = 200, nullable = false)
    @NotEmpty(message="El título es un campo requerido")
    private String title;

    @Column(name="message", length = 2000, nullable = false)
    @NotEmpty(message="El asunto es un campo requerido")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audit", nullable = false)
    private Audit audit;

    @OneToMany(mappedBy = "notification", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<NotificationReceiver> lstReceiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public List<NotificationReceiver> getLstReceiver() {
        return lstReceiver;
    }

    public void setLstReceiver(List<NotificationReceiver> lstReceiver) {
        this.lstReceiver = lstReceiver;
    }
}
