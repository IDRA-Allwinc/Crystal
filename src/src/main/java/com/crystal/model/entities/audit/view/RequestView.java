package com.crystal.model.entities.audit.view;

import com.crystal.model.shared.Constants;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Administrator on 1/5/2016.
 */
@Entity
@Subselect("select r.id_request, r.number, concat(substring(r.description, 1,30),'...') description, r.id_letter idLetter, r.is_attended isAttended, concat('', date(adddate(r.init_date, 0))) initDate, concat('', date(adddate(r.end_date, 0))) endDate, \n" +
        "case " +
        "when is_attended = 1 and attention_date < end_date then 'blue' " +
        "when is_attended = 1 and attention_date >  end_date then 'orange' " +
        "when is_attended = 0 and datediff(r.end_date, current_timestamp) <= " + Constants.redFlag + " then 'red'  " +
        "when is_attended = 0 and datediff(r.end_date, current_timestamp)  = " + Constants.yelllowFlag+ "  then 'yellow' " +
        "when is_attended = 0 and datediff(r.end_date, current_timestamp)  > " + Constants.yelllowFlag+ " then 'green' " +
        "else 'red' " +
        "end color " +
        "from request r where is_obsolete = 0")
public class RequestView {
    @Id
    @Column(name = "id_request")
    private Long id;

    private String number;

    private String description;

    private String color;

    private Long idLetter;

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

    public Long getIdLetter() {
        return idLetter;
    }

    public void setIdLetter(Long idLetter) {
        this.idLetter = idLetter;
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