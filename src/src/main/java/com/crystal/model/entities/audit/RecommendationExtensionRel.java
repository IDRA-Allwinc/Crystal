package com.crystal.model.entities.audit;

import javax.persistence.*;

@Entity
@Table(name="recommendation_extension_rel")
public class RecommendationExtensionRel {

    @Id
    @GeneratedValue
    @Column(name = "id_request_extension_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_request", nullable = false)
    private Recommendation recommendation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_extension", nullable = false)
    private Extension extension;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }
}
