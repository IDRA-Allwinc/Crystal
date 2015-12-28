package com.crystal.model.entities.audit;

import com.crystal.model.shared.UploadFileGeneric;

import javax.persistence.*;

@Entity
@Table(name="responsibility_upload_file_generic_rel")
public class ResponsibilityUploadFileGenericRel {

    @Id
    @GeneratedValue
    @Column(name = "id_responsibility_upload_file_generic_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsibility", nullable = false)
    private Responsibility responsibility;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_upload_file_generic", nullable = false)
    private UploadFileGeneric uploadFileGeneric;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Responsibility getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(Responsibility responsibility) {
        this.responsibility = responsibility;
    }

    public UploadFileGeneric getUploadFileGeneric() {
        return uploadFileGeneric;
    }

    public void setUploadFileGeneric(UploadFileGeneric uploadFileGeneric) {
        this.uploadFileGeneric = uploadFileGeneric;
    }
}
