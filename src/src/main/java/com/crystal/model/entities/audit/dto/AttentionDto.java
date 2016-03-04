package com.crystal.model.entities.audit.dto;

import com.crystal.model.shared.Constants;
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

    private String letterNumber;

    private String requestNumber;

    private String commentNumber;

    private String recommendationNumber;

    private String observationNumber;

    private String attentionUser;

    private transient SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private boolean isReplication;

    private String replicateAs;

    private Long observationTypeId;

    public AttentionDto() {

    }

    //para la atencion de oficios
    //para la atencion de observaciones
    public AttentionDto(Long id, String attentionComment, boolean isAttended, Calendar attentionDate, String attentionUser, String auditName, String entityNumber, Integer type) {
        this.id = id;
        this.attentionComment = attentionComment;
        this.isAttended = isAttended;
        if (attentionDate != null)
            this.attentionDateStr = sdf.format(attentionDate.getTime());
        this.attentionUser = attentionUser;
        this.auditName = auditName;
        switch (type) {
            case Constants.UploadFile.LETTER: {
                this.letterNumber = entityNumber;
                break;
            }
            case Constants.UploadFile.COMMENT: {
                this.commentNumber = entityNumber;
                break;
            }
            case Constants.UploadFile.RECOMMENDATION: {
                this.recommendationNumber = entityNumber;
                break;
            }
            case Constants.UploadFile.OBSERVATION: {
                this.observationNumber = entityNumber;
                break;
            }
        }
    }

    //para la atencion de requerimientos
    public AttentionDto(Long id, String attentionComment, boolean isAttended, Calendar attentionDate, String attentionUser, String auditName, String letterNumber, String requestNumber) {
        this.id = id;
        this.attentionComment = attentionComment;
        this.isAttended = isAttended;
        if (attentionDate != null)
            this.attentionDateStr = sdf.format(attentionDate.getTime());
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

    public String getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(String commentNumber) {
        this.commentNumber = commentNumber;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String getRecommendationNumber() {
        return recommendationNumber;
    }

    public void setRecommendationNumber(String recommendationNumber) {
        this.recommendationNumber = recommendationNumber;
    }

    public String getObservationNumber() {
        return observationNumber;
    }

    public void setObservationNumber(String observationNumber) {
        this.observationNumber = observationNumber;
    }

    public String getReplicateAs() {
        return replicateAs;
    }

    public void setReplicateAs(String replicateAs) {
        this.replicateAs = replicateAs;
    }

    public Long getObservationTypeId() {
        return observationTypeId;
    }

    public void setObservationTypeId(Long observationTypeId) {
        this.observationTypeId = observationTypeId;
    }

    public boolean isReplication() {
        return isReplication;
    }

    public void setReplication(boolean replication) {
        isReplication = replication;
    }
}
