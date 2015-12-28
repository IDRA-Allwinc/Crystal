package com.crystal.model.entities.audit;

import com.crystal.model.entities.account.User;
import com.crystal.model.shared.UploadFileGeneric;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name="extension")
public class Extension {

    @Id
    @GeneratedValue
    @Column(name = "id_extension")
    private Long id;

    @Column(name="create_date", length = 200, nullable = false)
    private Calendar createDate;

    @Column(name="is_obsolete", nullable = false)
    private boolean isObsolete;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User createUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_upload_file_generic", nullable = false)
    private UploadFileGeneric uploadFileGeneric;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public UploadFileGeneric getUploadFileGeneric() {
        return uploadFileGeneric;
    }

    public void setUploadFileGeneric(UploadFileGeneric uploadFileGeneric) {
        this.uploadFileGeneric = uploadFileGeneric;
    }
}
