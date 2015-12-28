package com.crystal.model.entities.audit;

import com.crystal.model.shared.UploadFileGeneric;

import javax.persistence.*;

@Entity
@Table(name="request_upload_file_generic_rel")
public class RequestUploadFileGenericRel {

    @Id
    @GeneratedValue
    @Column(name = "id_request_upload_file_generic_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_request", nullable = false)
    private Request request;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_upload_file_generic", nullable = false)
    private UploadFileGeneric uploadFileGeneric;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public UploadFileGeneric getUploadFileGeneric() {
        return uploadFileGeneric;
    }

    public void setUploadFileGeneric(UploadFileGeneric uploadFileGeneric) {
        this.uploadFileGeneric = uploadFileGeneric;
    }
}
