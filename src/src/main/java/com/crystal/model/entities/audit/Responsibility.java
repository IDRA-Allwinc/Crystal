package com.crystal.model.entities.audit;

import com.crystal.model.entities.account.User;
import com.crystal.model.entities.account.UserAuditInfo;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.ResponsibilityDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.shared.UploadFileGeneric;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name="responsibility")
public class Responsibility extends UserAuditInfo {

    @Id
    @GeneratedValue
    @Column(name = "id_responsibility")
    private Long id;

    @Column(name = "number", length = 50, nullable = false)
    @NotEmpty(message = "El numeral es un campo requerido")
    private String number;

    @Column(name="description", length = 2000, nullable = false)
    @NotEmpty(message="La descripción es un campo requerido")
    private String description;

    @Column(name="create_date", nullable = false)
    private Calendar createDate;

    @Column(name="init_date", nullable = false)
    private Calendar initDate;

    @Column(name="end_date", nullable = false)
    private Calendar endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = true)
    private User attentionUser;

    @Column(name="is_attended", nullable = false)
    private boolean isAttended;

    @Column(name="attention_date", nullable = true)
    private Calendar attentionDate;

    @Column(name = "attention_comment", nullable = true)
    private String attentionComment;

    @Column(name="is_obsolete", nullable = false)
    private boolean isObsolete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audit", nullable = false)
    private Audit audit;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comment", nullable = true)
    private Comment comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recommendation", nullable = true)
    private Recommendation recommendation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_observation", nullable = true)
    private Observation observation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "responsibility_area_rel",
            joinColumns = {@JoinColumn(name = "id_responsibility", referencedColumnName = "id_responsibility")},
            inverseJoinColumns = {@JoinColumn(name = "id_area", referencedColumnName = "id_area")})
    private List<Area> lstAreas;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "responsibility_upload_file_generic_rel",
            joinColumns = {@JoinColumn(name = "id_responsibility", referencedColumnName = "id_responsibility")},
            inverseJoinColumns = {@JoinColumn(name = "id_upload_file_generic", referencedColumnName = "id_upload_file_generic")})
    private List<UploadFileGeneric> lstEvidences;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "responsibility_extension_rel",
            joinColumns = {@JoinColumn(name = "id_responsibility", referencedColumnName = "id_responsibility")},
            inverseJoinColumns = {@JoinColumn(name = "id_extension", referencedColumnName = "id_extension")})
    private List<Extension> lstExtension;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Calendar getInitDate() {
        return initDate;
    }

    public void setInitDate(Calendar initDate) {
        this.initDate = initDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public User getAttentionUser() {
        return attentionUser;
    }

    public void setAttentionUser(User attentionUser) {
        this.attentionUser = attentionUser;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public void setAttended(boolean attended) {
        isAttended = attended;
    }

    public Calendar getAttentionDate() {
        return attentionDate;
    }

    public void setAttentionDate(Calendar attentionDate) {
        this.attentionDate = attentionDate;
    }

    public String getAttentionComment() {
        return attentionComment;
    }

    public void setAttentionComment(String attentionComment) {
        this.attentionComment = attentionComment;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean obsolete) {
        isObsolete = obsolete;
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

    public void merge(ResponsibilityDto responsibilityDto, AttentionDto attentionDto, User user) {

        if (responsibilityDto != null) {
            number = responsibilityDto.getNumber();
            description = responsibilityDto.getDescription();
        }

        if (attentionDto != null) {
            isAttended = true;
            attentionComment = attentionDto.getAttentionComment();
            attentionDate = Calendar.getInstance();
            attentionUser = user;
        }
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }
}
