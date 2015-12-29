package com.crystal.model.entities.audit;

import com.crystal.model.entities.catalog.Area;

import javax.persistence.*;

@Entity
@Table(name="request_area_rel")
public class RequestAreaRel {

    @Id
    @GeneratedValue
    @Column(name = "id_request_area_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_request", nullable = false)
    private Request request;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;

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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
