package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("SELECT uf.id_upload_file_generic id, uf.file_name fileName, uf.description, r.id_request requestId " +
        "FROM request r " +
        "INNER JOIN request_upload_file_generic_rel ru ON r.id_request = ru.id_request " +
        "INNER JOIN upload_file_generic uf ON uf.id_upload_file_generic = ru.id_upload_file_generic " )
public class RequestUploadFileView {
    @Id
    private Long id;
    private String fileName;
    private String description;
    private Long requestId;

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

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
}