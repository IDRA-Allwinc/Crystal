package com.crystal.model.shared;

public class UploadFileGenericDto {
    private Long id;
    private String fileName;

    public UploadFileGenericDto(Long id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

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
}