package com.crystal.model.entities.audit;

import com.crystal.model.entities.catalog.Area;

import javax.persistence.*;

@Entity
@Table(name="audit_area_rel")
public class AuditAreaRel {

    @Id
    @GeneratedValue
    @Column(name = "id_audit_area_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audit", nullable = false)
    private Audit audit;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
