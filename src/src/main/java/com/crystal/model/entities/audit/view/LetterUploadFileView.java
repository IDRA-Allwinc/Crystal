package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("SELECT uf.id_upload_file_generic id, uf.file_name fileName, uf.description, l.id_letter letterId, l.is_attended isAttended, rel.is_additional isAdditional " +
        "FROM letter_upload_file_generic_rel rel " +
        "INNER JOIN letter l on l.id_letter = rel.id_letter " +
        "INNER JOIN upload_file_generic uf ON rel.id_upload_file_generic = uf.id_upload_file_generic where uf.is_obsolete=false" )
public class LetterUploadFileView {
    @Id
    private Long id;
    private String fileName;
    private String description;
    private Long letterId;
    private Boolean isAttended;
    private Boolean isAdditional;

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

    public Boolean getIsAttended() {
        return isAttended;
    }

    public void setIsAttended(Boolean isAttended) {
        this.isAttended = isAttended;
    }

    public Long getLetterId() {
        return letterId;
    }

    public void setLetterId(Long letterId) {
        this.letterId = letterId;
    }

    public Boolean getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(Boolean isAdditional) {
        this.isAdditional = isAdditional;
    }
}