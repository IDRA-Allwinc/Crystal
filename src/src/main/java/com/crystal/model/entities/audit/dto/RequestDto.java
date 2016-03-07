package com.crystal.model.entities.audit.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RequestDto {

    private Long id;

    @NotEmpty(message = "El numeral es un campo requerido")
    @Length(min = 1, max = 8, message = "El numeral  debe tener entre 1 y 8 caracteres.")
    private String number;

    @NotEmpty(message = "La descripci&oacute;n es un campo requerido")
    @Length(min = 8, max = 2000, message = "La descripci&oacute;n debe tener entre 8 y 2000 caracteres.")
    private String description;

    @NotNull(message = "La fecha de inicio es un campo requerido")
    private String initDate;

    @NotNull(message = "La fecha líimite es un campo requerido")
    private String endDate;

    private String lstSelectedAreas;

    private Long letterId;

    private Integer type;

    private Boolean isAttended;

    public RequestDto() {
    }

    public RequestDto(Long id, String number, Boolean isAttended) {
        this.id = id;
        this.number = number;
        this.isAttended = isAttended;
    }

    public RequestDto(Long id, String number, String description, Calendar initDate, Calendar endDate, Long letterId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        this.id = id;
        this.number = number;
        this.description = description;
        this.initDate =sdf.format(initDate.getTime());
        this.endDate = sdf.format(endDate.getTime());
        this.letterId = letterId;
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

    public String getLstSelectedAreas() {
        return lstSelectedAreas;
    }

    public void setLstSelectedAreas(String lstSelectedAreas) {
        this.lstSelectedAreas = lstSelectedAreas;
    }

    public Long getLetterId() {
        return letterId;
    }

    public void setLetterId(Long letterId) {
        this.letterId = letterId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getIsAttended() {
        return isAttended;
    }

    public void setIsAttended(Boolean isAttended) {
        this.isAttended = isAttended;
    }
}
