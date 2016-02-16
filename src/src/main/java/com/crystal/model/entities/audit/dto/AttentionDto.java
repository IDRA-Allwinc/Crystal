package com.crystal.model.entities.audit.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AttentionDto {

    private Long id;

    @NotEmpty(message = "El comentario es un campo requerido")
    @Length(min = 8, max = 2000, message = "El comentario debe tener entre 8 y 2000 caracteres.")
    private String attentionComment;

    private boolean isAttended;

    private String attentionDateStr;

    private String auditName;

    private String requestNumber;

    private String letterNumber;

    private String attentionUser;

    public AttentionDto() {

    }

    //para la atencion de oficios
    public AttentionDto(Long id, String attentionComment, boolean isAttended, Calendar attentionDate, String attentionUser,String auditName, String letterNumber) {
        this.id = id;
        this.attentionComment = attentionComment;
        this.isAttended = isAttended;
        if (attentionDate != null)
            this.attentionDateStr = new SimpleDateFormat("dd/MM/yyyy").format(attentionDate.getTime());
        this.attentionUser = attentionUser;
        this.auditName = auditName;
        this.letterNumber = letterNumber;
    }

    //para la atencion de requerimientos
    public AttentionDto(Long id, String attentionComment, boolean isAttended, Calendar attentionDate, String attentionUser, String auditName, String letterNumber,  String requestNumber) {
        this.id = id;
        this.attentionComment = attentionComment;
        this.isAttended = isAttended;
        if (attentionDate != null)
            this.attentionDateStr = new SimpleDateFormat("dd/MM/yyyy").format(attentionDate.getTime());
        this.attentionUser = attentionUser;
        this.auditName = auditName;
        this.letterNumber = letterNumber;
        this.requestNumber = requestNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttentionComment() {
        return attentionComment;
    }

    public void setAttentionComment(String attentionComment) {
        this.attentionComment = attentionComment;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public void setAttended(boolean isAttended) {
        this.isAttended = isAttended;
    }

    public String getAttentionDateStr() {
        return attentionDateStr;
    }

    public void setAttentionDateStr(String attentionDateStr) {
        this.attentionDateStr = attentionDateStr;
    }

    public String getAttentionUser() {
        return attentionUser;
    }

    public void setAttentionUser(String attentionUser) {
        this.attentionUser = attentionUser;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getLetterNumber() {
        return letterNumber;
    }

    public void setLetterNumber(String letterNumber) {
        this.letterNumber = letterNumber;
    }
}
