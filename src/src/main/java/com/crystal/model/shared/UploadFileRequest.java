package com.crystal.model.shared;

public class UploadFileRequest {

    private String id;
    private String description;
    private Boolean closeUploadFile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
