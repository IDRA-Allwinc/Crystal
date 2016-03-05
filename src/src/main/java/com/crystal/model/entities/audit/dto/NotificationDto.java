package com.crystal.model.entities.audit.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

public class NotificationDto {

    private Long id;
    private Long auditId;

    @NotEmpty(message = "El título es un campo requerido")
    @Length(min = 5, max = 100, message = "El numeral  debe tener entre 8 y 50 caracteres.")
    private String title;

    @NotEmpty(message = "El mensaje es un campo requerido")
    @Length(min = 5, max = 2000, message = "El mensaje debe tener entre 5 y 2000 caracteres.")
    private String message;

    private List<Long> destinationIds;

    public NotificationDto() {

    }

    public NotificationDto(Long id, Long auditId, String title, String message) {
        this.id = id;
        this.auditId = auditId;
        this.title = title;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Long> getDestinationIds() {
        return destinationIds;
    }

    public void setDestinationIds(List<Long> destinationIds) {
        this.destinationIds = destinationIds;
    }
}
