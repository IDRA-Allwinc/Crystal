package com.crystal.model.entities.audit;

import com.crystal.model.entities.account.UserAuditInfo;
import com.crystal.model.shared.UploadFileGeneric;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
@Table(name="extension")
public class Extension extends UserAuditInfo{

    @Id
    @GeneratedValue
    @Column(name = "id_extension")
    private Long id;

    @Column(name="create_date", nullable = false)
    private Calendar createDate;

    @Column(name="end_date", nullable = false)
    @NotNull(message = "La fecha de fin es un campo requerido")
    private Calendar endDate;

    @Column(name = "comment", length = 2000, nullable = false)
    @NotEmpty(message = "El comentario es un campo requerido")
    private String comment;

    @Column(name="is_obsolete", nullable = false)
    private boolean isObsolete;

    @Column(name="is_initial", nullable = false)
    private boolean isInitial;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_upload_file_generic", nullable = true)
    private UploadFileGeneric uploadFileGeneric;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean isInitial) {
        this.isInitial = isInitial;
    }

    public UploadFileGeneric getUploadFileGeneric() {
        return uploadFileGeneric;
    }

    public void setUploadFileGeneric(UploadFileGeneric uploadFileGeneric) {
        this.uploadFileGeneric = uploadFileGeneric;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
