package com.crystal.model.entities.audit.dto;

import com.crystal.model.shared.SelectList;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AuditDto {

    private Long id;

    @NotEmpty(message = "El número del oficio es un campo requerido")
    @Size(min = 8, max = 200, message = "El número del oficio debe tener entre 8 y 200 caracteres")
    private String letterNumber;

    @NotNull(message = "La fecha del oficio es un campo requerido")
    private String letterDate;

    @NotEmpty(message = "El número de la aduitoría es un campo requerido")
    @Size(min = 2, max = 20, message = "El número de la aduitoría debe tener entre 2 y 20 caracteres")
    private String number;

    @NotEmpty(message = "El nombre de la aduitoría es un campo requerido")
    @Size(min = 8, max = 200, message = "El nombre de la aduitoría debe tener entre 8 y 200 caracteres")
    private String name;

    @NotEmpty(message = "El objetivo de la auditoría es un campo requerido")
    @Size(min = 8, max = 300, message = "El objetivo  de la aduitoría debe tener entre 8 y 300 caracteres")
    private String objective;

    @NotNull(message = "El periodo de la revisión es un campo requerido")
    private String reviewInitDate;

    @NotNull(message = "El periodo de la revisión es un campo requerido")
    private String reviewEndDate;

    @NotNull(message = "El año fiscalizado es un campo requerido")
    private String auditedYear;

    @NotEmpty(message = "El programa presupuestario es un campo requerido")
    @Size(min = 8, max = 300, message = "El programa presupuestario debe tener entre 8 y 200 caracteres")
    private String budgetProgram;

    @NotNull(message = "El órgano fiscalizador es un campo requerido")
    private Long supervisoryEntityId;

    @NotNull(message = "El ente fiscalizado es un campo requerido")
    private Long auditedEntityId;

    @NotNull(message = "El tipo de auditoria es un campo requerido")
    private Long auditTypeId;

    private SelectList supervisoryEntity;
    private SelectList auditedEntity;
    private SelectList auditType;
    private String lstSelectedAreas;
    private String role;

    public AuditDto() {
    }

    public AuditDto(Long id, String letterNumber, Calendar letterDate, String number, String name, String objective, Calendar reviewInitDate, Calendar reviewEndDate, Calendar auditedYear, String budgetProgram,
                    Long idSupervisoryEntity, String nameSupervisoryEntity, String responsibleSupervisoryEntity,
                    Long idAuditedEntity, String nameAuditedEntity, String responsibleAuditedEntity,
                    Long idAuditType, String nameAuditType) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        this.id = id;
        this.letterNumber = letterNumber;
        this.letterDate = sdf.format(letterDate.getTime());
        this.number = number;
        this.name = name;
        this.objective = objective;
        this.reviewInitDate = sdf.format(reviewInitDate.getTime());
        this.reviewEndDate = sdf.format(reviewEndDate.getTime());
        this.auditedYear = sdf.format(auditedYear.getTime());
        this.budgetProgram = budgetProgram;
        this.supervisoryEntity = new SelectList(idSupervisoryEntity, nameSupervisoryEntity, responsibleSupervisoryEntity);
        this.auditedEntity = new SelectList(idAuditedEntity, nameAuditedEntity, responsibleAuditedEntity);
        this.auditType = new SelectList(idAuditType, nameAuditType);
    }

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

    public String getLetterDate() {
        return letterDate;
    }

    public void setLetterDate(String letterDate) {
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

    public String getReviewInitDate() {
        return reviewInitDate;
    }

    public void setReviewInitDate(String reviewInitDate) {
        this.reviewInitDate = reviewInitDate;
    }

    public String getReviewEndDate() {
        return reviewEndDate;
    }

    public void setReviewEndDate(String reviewEndDate) {
        this.reviewEndDate = reviewEndDate;
    }

    public String getAuditedYear() {
        return auditedYear;
    }

    public void setAuditedYear(String auditedYear) {
        this.auditedYear = auditedYear;
    }

    public String getBudgetProgram() {
        return budgetProgram;
    }

    public void setBudgetProgram(String budgetProgram) {
        this.budgetProgram = budgetProgram;
    }

    public Long getSupervisoryEntityId() {
        return supervisoryEntityId;
    }

    public void setSupervisoryEntityId(Long supervisoryEntityId) {
        this.supervisoryEntityId = supervisoryEntityId;
    }

    public Long getAuditedEntityId() {
        return auditedEntityId;
    }

    public void setAuditedEntityId(Long auditedEntityId) {
        this.auditedEntityId = auditedEntityId;
    }

    public Long getAuditTypeId() {
        return auditTypeId;
    }

    public void setAuditTypeId(Long auditTypeId) {
        this.auditTypeId = auditTypeId;
    }

    public SelectList getSupervisoryEntity() {
        return supervisoryEntity;
    }

    public void setSupervisoryEntity(SelectList supervisoryEntity) {
        this.supervisoryEntity = supervisoryEntity;
    }

    public SelectList getAuditedEntity() {
        return auditedEntity;
    }

    public void setAuditedEntity(SelectList auditedEntity) {
        this.auditedEntity = auditedEntity;
    }

    public SelectList getAuditType() {
        return auditType;
    }

    public void setAuditType(SelectList auditType) {
        this.auditType = auditType;
    }

    public String getLstSelectedAreas() {
        return lstSelectedAreas;
    }

    public void setLstSelectedAreas(String lstSelectedAreas) {
        this.lstSelectedAreas = lstSelectedAreas;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
