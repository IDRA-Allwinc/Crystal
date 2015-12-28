package com.crystal.model.entities.audit;

import com.crystal.model.entities.catalog.Area;

import javax.persistence.*;

@Entity
@Table(name="recommendation_area_rel")
public class RecommendationAreaRel {

    @Id
    @GeneratedValue
    @Column(name = "id_recommendation_area_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recommendation", nullable = false)
    private Recommendation recommendation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;

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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
