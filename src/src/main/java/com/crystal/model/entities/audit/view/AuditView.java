package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
@Subselect("select a.id_audit, a.audited_year, a.letter_number, a.number, a.name, at.name as auditType, ae.id_audited_entity, aet.code, a.is_obsolete, date_format(audited_year, '%Y')as auditedYearStr " +
        "from audit a " +
        "inner join audit_type at on a.id_audit_type = at.id_audit_type " +
        "inner join audited_entity ae on a.id_audited_entity = ae.id_audited_entity " +
        "inner join audited_entity_type aet on ae.id_audited_entity_type = aet.id_audited_entity_type " +
        "where a.is_obsolete = false")
public class AuditView {
    @Id
    @Column(name = "id_audit")
    private Long id;
    @Column(name = "audited_year")
    private Calendar auditedYear;
    @Column(name = "letter_number")
    private String letterNumber;
    @Column(name = "number")
    private String number;
    @Column(name = "name")
    private String name;
    @Column(name = "auditType")
    private String auditType;
    @Column(name = "id_audited_entity")
    private Long auditedEntityId;
    @Column(name = "code")
    private String auditedEntityTypeCode;
    @Column(name = "is_obsolete")
    private boolean isObsolete;
    @Column(name = "auditedYearStr")
    private String auditedYearStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLetterNumber() {
        return letterNumber;
    }

    public void setLetterNumber(String letterNumber) {
        this.letterNumber = letterNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public Long getAuditedEntityId() {
        return auditedEntityId;
    }

    public void setAuditedEntityId(Long auditedEntityId) {
        this.auditedEntityId = auditedEntityId;
    }

    public String getAuditedEntityTypeCode() {
        return auditedEntityTypeCode;
    }

    public void setAuditedEntityTypeCode(String auditedEntityTypeCode) {
        this.auditedEntityTypeCode = auditedEntityTypeCode;
    }

    public Calendar getAuditedYear() {
        return auditedYear;
    }

    public void setAuditedYear(Calendar auditedYear) {
        this.auditedYear = auditedYear;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public String getAuditedYearStr() {
        return auditedYearStr;
    }

    public void setAuditedYearStr(String auditedYearStr) {
        this.auditedYearStr = auditedYearStr;
    }
}

