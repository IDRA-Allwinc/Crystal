package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select e.id_event, date_format(e.create_date, '%d/%m/%Y') createDate, et.name event, a.id_audit auditId, 'white' as color \n" +
        "from event e  \n" +
        "left join audit a on e.id_audit = a.id_audit \n" +
        "left join event_type et on e.id_event_type = et.id_event_type \n" +
        "where e.is_obsolete = 0")
public class EventView {
    @Id
    @Column(name = "id_event")
    private Long id;

    private String event;

    private Long auditId;

    private String color;

    private String createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
