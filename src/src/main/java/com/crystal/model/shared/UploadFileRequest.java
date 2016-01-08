package com.crystal.model.shared;

public class UploadFileRequest {

    private String description;
    private Boolean closeUploadFile;

    public Boolean getCloseUploadFile() {
        return closeUploadFile;
    }

    public void setCloseUploadFile(Boolean closeUploadFile) {
        this.closeUploadFile = closeUploadFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
