package com.crystal.model.entities.audit.view;

import com.crystal.model.shared.Constants;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select o.id_observation, o.number, concat(substring(o.description, 1,30),'...') description, date_format(o.init_date, '%d/%m/%Y') initDate, date_format(o.end_date, '%d/%m/%Y') endDate, o.is_attended isAttended, a.id_audit auditId, \n" +
        "case " +
        "when is_attended = 1 and o.attention_date <  o.end_date then 'blue-tr' " +
        "when is_attended = 1 and o.attention_date > o.end_date then 'orange-tr' " +
        "when is_attended = 0 and datediff(o.end_date, current_timestamp) <= " + Constants.redFlag + " then 'red-tr' " +
        "when is_attended = 0 and datediff(o.end_date, current_timestamp)  = " + Constants.yelllowFlag+ "  then 'yellow-tr' " +
        "when is_attended = 0 and datediff(o.end_date, current_timestamp)  > " + Constants.yelllowFlag+ " then 'green-tr' " +
        "end color \n" +
        "from observation o  \n" +
        "left join audit a on o.id_audit = a.id_audit \n" +
        "where o.is_obsolete = 0")
public class ObservationView {
    @Id
    @Column(name = "id_observation")
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
