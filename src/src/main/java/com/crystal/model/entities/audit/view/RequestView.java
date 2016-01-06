package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Administrator on 1/5/2016.
 */
@Entity
@Subselect("select id_request, name, description, concat('', create_date) create_date, concat('', attention_date) attention_date, is_attended, limit_time_days,\n" +
        "case\n" +
        "when is_attended = 1 and adddate(create_date, limit_time_days) < attention_date then 'blue'\n" +
        "when is_attended = 1 and adddate(create_date, limit_time_days) > attention_date then 'orange'\n" +
        "when is_attended = 0 and datediff(adddate(create_date, limit_time_days), current_timestamp) = 1 then 'red'\n" +
        "when is_attended = 0 and datediff(adddate(create_date, limit_time_days), current_timestamp) = 2 then 'yellow'\n" +
        "when is_attended = 0 and datediff(adddate(create_date, limit_time_days), current_timestamp) > 2 then 'green'\n" +
        "else ''\n" +
        "end color\n" +
        "from request where is_obsolete = 0")
public class RequestView {
    @Id
    @Column(name = "id_request")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private String creationDate;

    @Column(name = "attention_date")
    private String attentionDate;

    @Column(name = "is_attended")
    private boolean attended;

    @Column(name = "limit_time_days")
    private int limitTimeDays;

    @Column
    private String color;
}
