package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Administrator on 1/6/2016.
 */
@Entity
@Subselect("select id_letter, name, description,\n" +
        "case \n" +
        "when (select count(*) from request r where r.id_letter = l.id_letter and r.is_attended = 0) > 0 then 'orange'\n" +
        "else 'green'\n" +
        "end color\n" +
        "from \n" +
        "letter l\n" +
        "where\n" +
        "is_obsolete = 0")
public class LetterView {
    @Id
    @Column(name = "id_letter")
    private Long id;

    private String name;

    private String description;

    private String color;
}
