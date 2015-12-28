package com.crystal.model.entities.audit;

import com.crystal.model.shared.UploadFileGeneric;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="letter")
public class Letter {

    @Id
    @GeneratedValue
    @Column(name = "id_letter")
    private Long id;

    @Column(name="name", length = 200, unique = true, nullable = false)
    @NotEmpty(message="El número es un campo requerido")
    private String number;

    @Column(name="description", length = 200, nullable = false)
    @NotEmpty(message="La descripción es un campo requerido")
    private String description;

    @Column(name="is_obsolete", nullable = false)
    private boolean isObsolete;

    @Column(name="is_attended", nullable = false)
    private boolean isAttended;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audit", nullable = true)
    private Audit audit;

    @OneToMany(mappedBy = "letter", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Request> lstRequest;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "letter_upload_file_generic_rel",
            joinColumns = {@JoinColumn(name = "id_letter", referencedColumnName = "id_letter")},
            inverseJoinColumns = {@JoinColumn(name = "id_upload_file_generic", referencedColumnName = "id_upload_file_generic")})
    private List<UploadFileGeneric> lstFiles;


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

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public void setAttended(boolean isAttended) {
        this.isAttended = isAttended;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public List<Request> getLstRequest() {
        return lstRequest;
    }

    public void setLstRequest(List<Request> lstRequest) {
        this.lstRequest = lstRequest;
    }

    public List<UploadFileGeneric> getLstFiles() {
        return lstFiles;
    }

    public void setLstFiles(List<UploadFileGeneric> lstFiles) {
        this.lstFiles = lstFiles;
    }
}
