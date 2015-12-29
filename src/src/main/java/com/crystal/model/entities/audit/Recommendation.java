package com.crystal.model.entities.audit;

import com.crystal.model.entities.catalog.Area;
import com.crystal.model.shared.UploadFileGeneric;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name="recommendation")
public class Recommendation {

    @Id
    @GeneratedValue
    @Column(name = "id_recommendation")
    private Long id;

    @Column(name="description", length = 2000, nullable = false)
    @NotEmpty(message="La descripción es un campo requerido")
    private String description;

    @Column(name="create_date", nullable = false)
    private Calendar createDate;

    @Column(name="limit_time_days", nullable = false)
    @NotEmpty(message="El plazo otorgado es un campo requerido")
    private Integer limitTimeDays;

    @Column(name="is_attended", nullable = false)
    private boolean isAttended;

    @Column(name="attention_date", nullable = true)
    private Calendar attentionDate;

    @Column(name="is_obsolete", nullable = false)
    private boolean isObsolete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audit", nullable = false)
    private Audit audit;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "recommendation_area_rel",
            joinColumns = {@JoinColumn(name = "id_recommendation", referencedColumnName = "id_recommendation")},
            inverseJoinColumns = {@JoinColumn(name = "id_area", referencedColumnName = "id_area")})
    private List<Area> lstAreas;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "recommendation_upload_file_generic_rel",
            joinColumns = {@JoinColumn(name = "id_recommendation", referencedColumnName = "id_recommendation")},
            inverseJoinColumns = {@JoinColumn(name = "id_upload_file_generic", referencedColumnName = "id_upload_file_generic")})
    private List<UploadFileGeneric> lstEvidences;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "recommendation_extension_rel",
            joinColumns = {@JoinColumn(name = "id_recommendation", referencedColumnName = "id_recommendation")},
            inverseJoinColumns = {@JoinColumn(name = "id_extension", referencedColumnName = "id_extension")})
    private List<Extension> lstExtension;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLimitTimeDays() {
        return limitTimeDays;
    }

    public void setLimitTimeDays(Integer limitTimeDays) {
        this.limitTimeDays = limitTimeDays;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public void setAttended(boolean isAttended) {
        this.isAttended = isAttended;
    }

    public Calendar getAttentionDate() {
        return attentionDate;
    }

    public void setAttentionDate(Calendar attentionDate) {
        this.attentionDate = attentionDate;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public List<Area> getLstAreas() {
        return lstAreas;
    }

    public void setLstAreas(List<Area> lstAreas) {
        this.lstAreas = lstAreas;
    }

    public List<UploadFileGeneric> getLstEvidences() {
        return lstEvidences;
    }

    public void setLstEvidences(List<UploadFileGeneric> lstEvidences) {
        this.lstEvidences = lstEvidences;
    }

    public List<Extension> getLstExtension() {
        return lstExtension;
    }

    public void setLstExtension(List<Extension> lstExtension) {
        this.lstExtension = lstExtension;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }
}
