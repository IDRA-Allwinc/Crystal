package com.crystal.model.entities.audit;

import com.crystal.model.entities.account.UserAuditInfo;
import com.crystal.model.entities.catalog.*;
import com.crystal.model.shared.UploadFileGeneric;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name="event")
public class Event extends UserAuditInfo {

    @Id
    @GeneratedValue
    @Column(name = "id_event")
    private Long id;

    @Column(name="description", length = 2000, nullable = false)
    @NotEmpty(message="Los comentarios es un campo requerido")
    private String description;

    @Column(name="meeting_date", nullable = true)
    private Calendar meetingDate;

    @Column(name="create_date", nullable = false)
    private Calendar createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audit", nullable = false)
    private Audit audit;

    @Column(name="is_obsolete", nullable = false)
    private boolean isObsolete;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_event_type", nullable = false)
    @NotNull(message="El tipo de evento es un campo requerido")
    private EventType eventType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_meeting_type", nullable = true)
    private MeetingType meetingType;

    @OneToMany(mappedBy = "event", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Assistant> lstAssistant;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "event_upload_file_generic_rel",
            joinColumns = {@JoinColumn(name = "id_event", referencedColumnName = "id_event")},
            inverseJoinColumns = {@JoinColumn(name = "id_upload_file_generic", referencedColumnName = "id_upload_file_generic")})
    private List<UploadFileGeneric> lstFiles;

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

    public Calendar getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Calendar meetingDate) {
        this.meetingDate = meetingDate;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    public List<Assistant> getLstAssistant() {
        return lstAssistant;
    }

    public void setLstAssistant(List<Assistant> lstAssistant) {
        this.lstAssistant = lstAssistant;
    }

    public List<UploadFileGeneric> getLstFiles() {
        return lstFiles;
    }

    public void setLstFiles(List<UploadFileGeneric> lstFiles) {
        this.lstFiles = lstFiles;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean obsolete) {
        isObsolete = obsolete;
    }

}
