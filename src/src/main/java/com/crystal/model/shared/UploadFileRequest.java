package com.crystal.model.shared;

public class UploadFileRequest {

    private Long id;
    private String description;
    private String extensionComment;
    private String endDate;
    private Integer type;
    private Boolean closeUploadFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExtensionComment() {
        return extensionComment;
    }

    public void setExtensionComment(String extensionComment) {
        this.extensionComment = extensionComment;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
