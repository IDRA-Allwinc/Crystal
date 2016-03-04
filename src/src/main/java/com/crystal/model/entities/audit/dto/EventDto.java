package com.crystal.model.entities.audit.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventDto {

    private Long id;


    @NotEmpty(message = "Los comentarios es un campo requerido")
    @Length(min = 8, max = 2000, message = "Los comentarios deben tener entre 8 y 2000 caracteres.")
    private String description;

    private Long auditId;

    private Long eventTypeId;

    private Integer type;

    private String meetingDate;

    private String meetingHour;

    private Long meetingTypeId;

    private String lstSelectedAssistants;

    public EventDto() {

    }

    public EventDto(Long id, String description, Long auditId, Long eventTypeId, Calendar meetingDate, Long meetingTypeId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdh = new SimpleDateFormat("HH:mm:ss");

        this.id = id;
        this.description = description;
        this.auditId = auditId;
        this.eventTypeId = eventTypeId;
        this.meetingTypeId = meetingTypeId;
        if(meetingDate != null){
            this.meetingDate = sdf.format(meetingDate.getTime());
            this.meetingHour = sdh.format(meetingDate.getTime());
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingHour() {
        return meetingHour;
    }

    public void setMeetingHour(String meetingHour) {
        this.meetingHour = meetingHour;
    }

    public Long getMeetingTypeId() {
        return meetingTypeId;
    }

    public void setMeetingTypeId(Long meetingTypeId) {
        this.meetingTypeId = meetingTypeId;
    }

    public String getLstSelectedAssistants() {
        return lstSelectedAssistants;
    }

    public void setLstSelectedAssistants(String lstSelectedAssistants) {
        this.lstSelectedAssistants = lstSelectedAssistants;
    }
}
