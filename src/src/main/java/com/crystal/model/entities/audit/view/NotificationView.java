package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
@Subselect("select e.id_notification, e.title, e.message, e.id_audit auditId, DATE_FORMAT(e.date_time_ins, '%Y-%m-%d %H:%i:%S') calIns \n" +
        "from notification e  \n" +
        "where e.is_obsolete = 0")
public class NotificationView {
    @Id
    @Column(name = "id_notification")
    private Long id;

    private String title;

    private String message;

    private Long auditId;

    private String calIns;

    public NotificationView() {
    }

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

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getCalIns() {
        return calIns;
    }

    public void setCalIns(String calIns) {
        this.calIns = calIns;
    }
}
