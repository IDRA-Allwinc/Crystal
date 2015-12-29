package com.crystal.model.entities.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_file_type")
public class CatFileType {
    @Id
    @Column(name = "id_cat_file_type")
    private Long id;

    @Column(name = "file_type", length = 255, nullable = false)
    private String fileType;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "code", length = 255, nullable = false)
    private String code;

    @Column(name = "is_obsolete")
    private boolean isObsolete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
