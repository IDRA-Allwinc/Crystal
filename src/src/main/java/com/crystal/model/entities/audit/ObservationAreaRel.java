package com.crystal.model.entities.audit;

import com.crystal.model.entities.catalog.Area;

import javax.persistence.*;

@Entity
@Table(name="observation_area_rel")
public class ObservationAreaRel {

    @Id
    @GeneratedValue
    @Column(name = "id_observation_area_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_observation", nullable = false)
    private Observation observation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
