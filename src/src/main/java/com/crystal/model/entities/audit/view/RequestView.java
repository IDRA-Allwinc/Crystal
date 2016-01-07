package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Administrator on 1/5/2016.
 */
@Entity
@Subselect("select id_request, name, description, id_letter idLetter, concat('', date(adddate(create_date, limit_time_days))) deadLine, \n" +
        "case\n" +
        "when is_attended = 1 and adddate(create_date, limit_time_days) < attention_date then 'blue'\n" +
        "when is_attended = 1 and adddate(create_date, limit_time_days) > attention_date then 'orange'\n" +
        "when is_attended = 0 and datediff(adddate(create_date, limit_time_days), current_timestamp) <= 1 then 'red'\n" +
        "when is_attended = 0 and datediff(adddate(create_date, limit_time_days), current_timestamp) = 2 then 'yellow'\n" +
        "when is_attended = 0 and datediff(adddate(create_date, limit_time_days), current_timestamp) > 2 then 'green'\n" +
        "else 'red'\n" +
        "end color\n" +
        "from request where is_obsolete = 0")
public class RequestView {
    @Id
    @Column(name = "id_request")
    private Long id;

    private String name;

    private String description;

    private String color;

    private Long idLetter;

    private String deadLine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}