package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("SELECT uf.id_upload_file_generic id, uf.file_name fileName, uf.description, r.id_recommendation recommendationId, r.is_attended isAttended " +
        "FROM recommendation r " +
        "INNER JOIN recommendation_upload_file_generic_rel ru ON r.id_recommendation = ru.id_recommendation " +
        "INNER JOIN upload_file_generic uf ON uf.id_upload_file_generic = ru.id_upload_file_generic " +
        "where uf.is_obsolete=false")
public class RecommendationUploadFileView {
    @Id
    private Long id;
    private String fileName;
    private String description;
    private Long recommendationId;
    private Boolean isAttended;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsAttended() {
        return isAttended;
    }

    public void setIsAttended(Boolean isAttended) {
        this.isAttended = isAttended;
    }

    public Long getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(Long recommendationId) {
        this.recommendationId = recommendationId;
    }
}
