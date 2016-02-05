package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select l.id_letter, l.name, l.description, r.id_role roleId, a.id_audit as auditId,\n" +
        "case \n" +
        "when (l.is_attended = true) > 0 then 'blue'\n" +
        "when (select count(*) from request rq where rq.id_letter = l.id_letter and rq.is_attended = 0 and rq.is_obsolete = 0) > 0 then 'orange'\n" +
        "else 'green'\n" +
        "end color, \n" +
        "l.is_attended as isAttended \n" +
        "from letter l \n" +
        "inner join role r on l.id_role = r.id_role \n" +
        "left join audit a on l.id_audit = a.id_audit \n" +
        "where l.is_obsolete = 0")
public class LetterAuditView {
    @Id
    @Column(name = "id_letter")
    private Long id;

    private String name;

    private String description;

    private String color;

    private Long roleId;

    private Long auditId;

    private boolean isAttended;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
}
