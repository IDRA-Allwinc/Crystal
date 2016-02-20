package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("SELECT uf.id_upload_file_generic id, uf.file_name fileName, uf.description, c.id_comment commentId, c.is_attended isAttended " +
        "FROM comment c " +
        "INNER JOIN comment_upload_file_generic_rel cu ON c.id_comment = cu.id_comment " +
        "INNER JOIN upload_file_generic uf ON uf.id_upload_file_generic = cu.id_upload_file_generic " +
        "where uf.is_obsolete=false")
public class CommentUploadFileView {
    @Id
    private Long id;
    private String fileName;
    private String description;
    private Long commentId;
    private Boolean isAttended;

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

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}