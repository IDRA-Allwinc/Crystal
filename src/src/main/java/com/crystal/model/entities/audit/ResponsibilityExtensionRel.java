package com.crystal.model.entities.audit;

import javax.persistence.*;

@Entity
@Table(name="responsibility_extension_rel")
public class ResponsibilityExtensionRel {

    @Id
    @GeneratedValue
    @Column(name = "id_responsibility_extension_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsibility", nullable = false)
    private Responsibility responsibility;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_extension", nullable = false)
    private Extension extension;

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

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }
}
