package com.crystal.model.entities.audit;

import javax.persistence.*;

@Entity
@Table(name="request_extension_rel")
public class RequestExtensionRel {

    @Id
    @GeneratedValue
    @Column(name = "id_request_extension_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_request", nullable = false)
    private Request request;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_extension", nullable = false)
    private Extension extension;

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

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }
}
