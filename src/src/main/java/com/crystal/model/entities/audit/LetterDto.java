package com.crystal.model.entities.audit;

import com.crystal.model.shared.UploadFileGenericDto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class LetterDto {

    private Long id;

    @NotEmpty(message = "El número de oficio es un campo requerido.")
    @Length(min = 2, max  = 200, message = "El número de oficio debe tener entre 2 y 200 caracteres.")
    private String number;

    @NotEmpty(message = "La descripción es un campo requerido.")
    @Length(min = 1, max  = 200, message = "La descripción debe tener entre 2 y 200 caracteres.")
    private String description;

    @Valid
    @NotNull(message = "El archivo es un campo requerido.")
    private List<UploadFileGenericDto> lstFiles;

    public LetterDto() {
    }

    public LetterDto(Long id, String number, String description) {
        this.id = id;
        this.number = number;
        this.description = description;
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

    public List<UploadFileGenericDto> getLstFiles() {
        return lstFiles;
    }

    public void setLstFiles(List<UploadFileGenericDto> lstFiles) {
        this.lstFiles = lstFiles;
    }
}
