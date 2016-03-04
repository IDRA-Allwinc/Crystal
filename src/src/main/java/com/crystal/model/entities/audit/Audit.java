package com.crystal.model.entities.audit;

import com.crystal.model.entities.account.UserAuditInfo;
import com.crystal.model.entities.audit.dto.AuditDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.entities.catalog.AuditType;
import com.crystal.model.entities.catalog.AuditedEntity;
import com.crystal.model.entities.catalog.SupervisoryEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "audit")
public class Audit extends UserAuditInfo {

    @Id
    @GeneratedValue
    @Column(name = "id_audit")
    private Long id;

    @Column(name = "letter_number", length = 200, nullable = false)
    @NotEmpty(message = "El número del oficio es un campo requerido")
    private String letterNumber;

    @Column(name = "letter_date", nullable = false)
    @NotNull(message = "La fecha del oficio es un campo requerido")
    private Calendar letterDate;

    @Column(name = "number", length = 20, nullable = false)
    @NotEmpty(message = "El número de la aduitoría es un campo requerido")
    private String number;

    @Column(name = "name", length = 210, nullable = false)
    @NotEmpty(message = "El nombre de la aduitoría es un campo requerido")
    private String name;

    @Column(name = "objective", length = 310, nullable = false)
    @NotEmpty(message = "El objetivo de la auditoría es un campo requerido")
    private String objective;

    @Column(name = "review_init_date", nullable = false)
    @NotNull(message = "El periodo de la revisión es un campo requerido")
    private Calendar reviewInitDate;

    @Column(name = "review_end_date", nullable = false)
    @NotNull(message = "El periodo de la revisión es un campo requerido")
    private Calendar reviewEndDate;

    @Column(name = "audited_year", nullable = false)
    @NotNull(message = "El año fiscalizado es un campo requerido")
    private Calendar auditedYear;

    @Column(name = "budget_program", length = 310, nullable = false)
    @NotEmpty(message = "El programa presupuestario es un campo requerido")
    private String budgetProgram;

    @Column(name = "is_obsolete", nullable = false)
    private boolean isObsolete;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_supervisory_entity", nullable = false)
    @NotNull(message = "El órgano fiscalizador es un campo requerido")
    private SupervisoryEntity supervisoryEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audit_type", nullable = false)
    @NotNull(message = "El tipo de auditoria es un campo requerido")
    private AuditType auditType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audited_entity", nullable = false)
    @NotNull(message = "El ente fiscalizado es un campo requerido")
    private AuditedEntity auditedEntity;

    @OneToMany(mappedBy = "audit", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> lstComment;

    @OneToMany(mappedBy = "audit", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Event> lstEvent;

    @OneToMany(mappedBy = "audit", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Letter> lstLetter;

    @OneToMany(mappedBy = "audit", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Notification> lstNotification;

    @OneToMany(mappedBy = "audit", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Observation> lstObservation;

    @OneToMany(mappedBy = "audit", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Recommendation> lstRecommendation;

    @OneToMany(mappedBy = "audit", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Responsibility> lstResponsibility;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "audit_area_rel",
            joinColumns = {@JoinColumn(name = "id_audit", referencedColumnName = "id_audit", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_area", referencedColumnName = "id_area", nullable = false)})
    private List<Area> lstAreas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLetterNumber() {
        return letterNumber;
    }

    public void setLetterNumber(String letterNumber) {
        this.letterNumber = letterNumber;
    }

    public Calendar getLetterDate() {
        return letterDate;
    }

    public void setLetterDate(Calendar letterDate) {
        this.letterDate = letterDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public Calendar getReviewInitDate() {
        return reviewInitDate;
    }

    public void setReviewInitDate(Calendar reviewInitDate) {
        this.reviewInitDate = reviewInitDate;
    }

    public Calendar getReviewEndDate() {
        return reviewEndDate;
    }

    public void setReviewEndDate(Calendar reviewEndDate) {
        this.reviewEndDate = reviewEndDate;
    }

    public Calendar getAuditedYear() {
        return auditedYear;
    }

    public void setAuditedYear(Calendar auditedYear) {
        this.auditedYear = auditedYear;
    }

    public String getBudgetProgram() {
        return budgetProgram;
    }

    public void setBudgetProgram(String budgetProgram) {
        this.budgetProgram = budgetProgram;
    }

    public SupervisoryEntity getSupervisoryEntity() {
        return supervisoryEntity;
    }

    public void setSupervisoryEntity(SupervisoryEntity supervisoryEntity) {
        this.supervisoryEntity = supervisoryEntity;
    }

    public AuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(AuditType auditType) {
        this.auditType = auditType;
    }

    public AuditedEntity getAuditedEntity() {
        return auditedEntity;
    }

    public void setAuditedEntity(AuditedEntity auditedEntity) {
        this.auditedEntity = auditedEntity;
    }

    public List<Comment> getLstComment() {
        return lstComment;
    }

    public void setLstComment(List<Comment> lstComment) {
        this.lstComment = lstComment;
    }

    public List<Event> getLstEvent() {
        return lstEvent;
    }

    public void setLstEvent(List<Event> lstEvent) {
        this.lstEvent = lstEvent;
    }

    public List<Letter> getLstLetter() {
        return lstLetter;
    }

    public void setLstLetter(List<Letter> lstLetter) {
        this.lstLetter = lstLetter;
    }

    public List<Notification> getLstNotification() {
        return lstNotification;
    }

    public void setLstNotification(List<Notification> lstNotification) {
        this.lstNotification = lstNotification;
    }

    public List<Observation> getLstObservation() {
        return lstObservation;
    }

    public void setLstObservation(List<Observation> lstObservation) {
        this.lstObservation = lstObservation;
    }

    public List<Recommendation> getLstRecommendation() {
        return lstRecommendation;
    }

    public void setLstRecommendation(List<Recommendation> lstRecommendation) {
        this.lstRecommendation = lstRecommendation;
    }

    public List<Responsibility> getLstResponsibility() {
        return lstResponsibility;
    }

    public void setLstResponsibility(List<Responsibility> lstResponsibility) {
        this.lstResponsibility = lstResponsibility;
    }

    public List<Area> getLstAreas() {
        return lstAreas;
    }

    public void setLstAreas(List<Area> lstAreas) {
        this.lstAreas = lstAreas;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public void merge(AuditDto auditDto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdfM = new SimpleDateFormat("yyyy/MM");
        SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
        this.letterNumber = auditDto.getLetterNumber();
        this.number = auditDto.getNumber();
        this.name = auditDto.getName();
        this.objective = auditDto.getObjective();
        this.budgetProgram = auditDto.getBudgetProgram();
        this.letterDate = Calendar.getInstance();
        this.reviewInitDate = Calendar.getInstance();
        this.reviewEndDate = Calendar.getInstance();
        this.auditedYear = Calendar.getInstance();
        this.letterDate.setTime(sdf.parse(auditDto.getLetterDate()));
        this.reviewInitDate.setTime(sdfM.parse(auditDto.getReviewInitDate()));
        this.reviewEndDate.setTime(sdfM.parse(auditDto.getReviewEndDate()));
        this.auditedYear.setTime(sdfY.parse(auditDto.getAuditedYear()));
    }
}
