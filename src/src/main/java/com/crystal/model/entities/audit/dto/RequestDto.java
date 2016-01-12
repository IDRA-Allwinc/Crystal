package com.crystal.model.entities.audit.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class RequestDto {

    private Long id;

    @NotEmpty(message = "El numeral es un campo requerido")
    private String number;

    @NotEmpty(message = "El nombre es un campo requerido")
    @Length(min = 8, max = 200, message = "El nombre debe tener entre 8 y 2000 caracteres.")
    private String description;

    @NotNull(message = "El plazo otorgado es un campo requerido")
    private Integer limitTimeDays;

    private String lstSelectedAreas;

    private Long letterId;

    public RequestDto() {
    }

    public RequestDto(Long id, String number, String description, Integer limitTimeDays, Long letterId) {
        this.id = id;
        this.number = number;
        this.description = description;
        this.limitTimeDays = limitTimeDays;
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

    public Integer getLimitTimeDays() {
        return limitTimeDays;
    }

    public void setLimitTimeDays(Integer limitTimeDays) {
        this.limitTimeDays = limitTimeDays;
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
}
