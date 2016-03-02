package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("SELECT uf.id_upload_file_generic id, uf.file_name fileName, uf.description, e.id_event eventId " +
        "FROM event e " +
        "INNER JOIN event_upload_file_generic_rel cu ON e.id_event = cu.id_event " +
        "INNER JOIN upload_file_generic uf ON uf.id_upload_file_generic = cu.id_upload_file_generic " +
        "where uf.is_obsolete=false")
public class EventUploadFileView {
    @Id
    private Long id;
    private String fileName;
    private String description;
    private Long eventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}