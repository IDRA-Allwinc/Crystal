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
@Subselect("select id_request, number, description, id_letter idLetter, is_attended isAttended, concat('', date(adddate(create_date, limit_time_days))) deadLine, \n" +
        "case " +
        "when is_attended = 1 and attention_date < adddate(create_date, limit_time_days) then 'blue' " +
        "when is_attended = 1 and attention_date >  adddate(create_date, limit_time_days) then 'orange' " +
        "when is_attended = 0 and datediff(adddate(create_date, limit_time_days), current_timestamp) <= " + Constants.redFlag + " then 'red' " +
        "when is_attended = 0 and datediff(adddate(create_date, limit_time_days), current_timestamp) = " + Constants.yelllowFlag+ "  then 'yellow' " +
        "when is_attended = 0 and datediff(adddate(create_date, limit_time_days), current_timestamp) > " + Constants.yelllowFlag+ " then 'green' " +
        "else 'red' " +
        "end color " +
        "from request where is_obsolete = 0")
public class RequestView {
    @Id
    @Column(name = "id_request")
    private Long id;

    private String number;

    private String description;

    private String color;

    private Long idLetter;

    private String deadLine;

    private boolean isAttended;

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

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public void setAttended(boolean isAttended) {
        this.isAttended = isAttended;
    }
}