package com.crystal.model.entities.audit;

import com.crystal.model.shared.UploadFileGenericDto;
import java.util.List;

public class LetterDto {

    private Long id;

    private String number;

    private String description;

    private List<UploadFileGenericDto> lstFiles;

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
