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

    private String attentionUser;

    public AttentionDto() {

    }

    public AttentionDto(Long id, String attentionComment, boolean isAttended, Calendar attentionDate, String attentionUser) {
        this.id = id;
        this.attentionComment = attentionComment;
        this.isAttended = isAttended;
        if (attentionDate != null)
            this.attentionDateStr = new SimpleDateFormat("dd/MM/yyyy").format(attentionDate.getTime());
        this.attentionUser = attentionUser;
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
}
