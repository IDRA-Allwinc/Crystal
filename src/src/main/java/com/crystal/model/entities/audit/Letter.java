package com.crystal.model.entities.audit;

import com.crystal.model.entities.account.Role;
import com.crystal.model.entities.account.User;
import com.crystal.model.entities.account.UserAuditInfo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "letter")
public class Letter extends UserAuditInfo{

    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_letter")
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    @NotEmpty(message = "El número es un campo requerido")
    private String number;

    @Column(name = "description", length = 200, nullable = false)
    @NotEmpty(message = "La descripción es un campo requerido")
    private String description;

    @Column(name = "is_obsolete", nullable = false)
    private boolean isObsolete;

    @Column(name = "is_attended", nullable = false)
    private boolean isAttended;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audit", nullable = true)
    private Audit audit;

    @OneToMany(mappedBy = "letter", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Request> lstRequest;

    @OneToMany(mappedBy = "letter", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<LetterUploadFileGenericRel> lstFiles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_role", nullable = false)
    private Role role;

    @Column(name = "attention_date", nullable = true)
    private Calendar attentionDate;

    @Column(name = "attention_comment", nullable = true)
    private String attentionComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = true)
    private User attentionUser;

    public Letter() {
    }

    public Letter(Long roleId) {
        role = new Role(roleId);
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

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public List<LetterUploadFileGenericRel> getLstFiles() {
        return lstFiles;
    }

    public void setLstFiles(List<LetterUploadFileGenericRel> lstFiles) {
        this.lstFiles = lstFiles;
    }



    public void merge(LetterDto modelNew) {
        number = modelNew.getNumber();
        description = modelNew.getDescription();
    }

    public Calendar getAttentionDate() {
        return attentionDate;
    }

    public void setAttentionDate(Calendar attentionDate) {
        this.attentionDate = attentionDate;
    }

    public String getAttentionComment() {
        return attentionComment;
    }

    public void setAttentionComment(String attentionComment) {
        this.attentionComment = attentionComment;
    }

    public User getAttentionUser() {
        return attentionUser;
    }

    public void setAttentionUser(User attentionUser) {
        this.attentionUser = attentionUser;
    }
}
