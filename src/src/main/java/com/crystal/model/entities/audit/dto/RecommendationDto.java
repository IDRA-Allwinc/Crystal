package com.crystal.model.entities.audit.dto;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RecommendationDto {

    private Long id;

    @NotEmpty(message = "El numeral es un campo requerido")
    @Length(min = 8, max = 50, message = "El numeral  debe tener entre 8 y 50 caracteres.")
    private String number;

    @NotEmpty(message = "La descripci&oacute;n es un campo requerido")
    @Length(min = 8, max = 2000, message = "La descripci&oacute;n debe tener entre 8 y 2000 caracteres.")
    private String description;

    @NotNull(message = "La fecha de inicio es un campo requerido")
    private String initDate;

    @NotNull(message = "La fecha líimite es un campo requerido")
    private String endDate;

    private String lstSelectedAreas;

    private Long auditId;

    public RecommendationDto() {

    }

    public RecommendationDto(Long id, String number, String description, Calendar initDate, Calendar endDate, Long auditId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        this.id = id;
        this.number = number;
        this.description = description;
        this.initDate =sdf.format(initDate.getTime());
        this.endDate = sdf.format(endDate.getTime());
        this.auditId = auditId;
    }

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

    public String getLstSelectedAreas() {
        return lstSelectedAreas;
    }

    public void setLstSelectedAreas(String lstSelectedAreas) {
        this.lstSelectedAreas = lstSelectedAreas;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
