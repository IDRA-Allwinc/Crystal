package com.crystal.model.entities.audit.view;

import com.crystal.model.shared.Constants;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select c.id_responsibility, c.number, concat(substring(c.description, 1,30),'...') description, concat('', date(adddate(c.init_date, 0))) initDate, concat('', date(adddate(c.end_date, 0))) endDate, c.is_attended isAttended, a.id_audit auditId,\n" +
        "case " +
        "when is_attended = 1 and c.attention_date <  c.end_date then 'blue' " +
        "when is_attended = 1 and c.attention_date > c.end_date then 'orange' " +
        "when is_attended = 0 and datediff(c.end_date, current_timestamp) <= " + Constants.redFlag + " then 'red' " +
        "when is_attended = 0 and datediff(c.end_date, current_timestamp)  = " + Constants.yelllowFlag+ "  then 'yellow' " +
        "when is_attended = 0 and datediff(c.end_date, current_timestamp)  > " + Constants.yelllowFlag+ " then 'green' " +
        "end color \n" +
        "from responsibility c  \n" +
        "left join audit a on c.id_audit = a.id_audit \n" +
        "where c.is_obsolete = 0")
public class ResponsibilityView {
    @Id
    @Column(name = "id_responsibility")
    private Long id;

    private String number;

    private String description;

    private String color;

    private Long auditId;

    private boolean isAttended;

    private String initDate;

    private String endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public void setAttended(boolean isAttended) {
        this.isAttended = isAttended;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
