package com.crystal.model.entities.audit;

import javax.persistence.*;

@Entity
@Table(name="observation_extension_rel")
public class ObservationExtensionRel {

    @Id
    @GeneratedValue
    @Column(name = "id_observation_extension_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_observation", nullable = false)
    private Observation observation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_extension", nullable = false)
    private Extension extension;

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

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }
}
