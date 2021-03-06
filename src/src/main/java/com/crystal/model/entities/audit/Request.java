package com.crystal.model.entities.audit;

import com.crystal.model.entities.account.User;
import com.crystal.model.entities.account.UserAuditInfo;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RequestDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.shared.UploadFileGeneric;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "request")
public class Request extends UserAuditInfo {

    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_request")
    private Long id;

    @Column(name = "number", length = 200, nullable = false)
    @NotEmpty(message = "El numeral es un campo requerido")
    private String number;

    @Column(name = "description", length = 2000, nullable = false)
    @NotEmpty(message = "La descripción es un campo requerido")
    private String description;

    @Column(name = "create_date", nullable = false)
    private Calendar createDate;

    @Column(name="init_date", nullable = false)
    private Calendar initDate;

    @Column(name="end_date", nullable = false)
    private Calendar endDate;

    @Column(name = "is_attended", nullable = false)
    private boolean isAttended;

    @Column(name = "attention_date", nullable = true)
    private Calendar attentionDate;

    @Column(name = "attention_comment", nullable = true)
    private String attentionComment;

    @Column(name = "is_obsolete", nullable = false)
    private boolean isObsolete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_letter", nullable = false)
    private Letter letter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = true)
    private User attentionUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "request_area_rel",
            joinColumns = {@JoinColumn(name = "id_request", referencedColumnName = "id_request")},
            inverseJoinColumns = {@JoinColumn(name = "id_area", referencedColumnName = "id_area")})
    private List<Area> lstAreas;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "request_upload_file_generic_rel",
            joinColumns = {@JoinColumn(name = "id_request", referencedColumnName = "id_request")},
            inverseJoinColumns = {@JoinColumn(name = "id_upload_file_generic", referencedColumnName = "id_upload_file_generic")})
    private List<UploadFileGeneric> lstEvidences;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "request_extension_rel",
            joinColumns = {@JoinColumn(name = "id_request", referencedColumnName = "id_request")},
            inverseJoinColumns = {@JoinColumn(name = "id_extension", referencedColumnName = "id_extension")})
    private List<Extension> lstExtension;

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

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public void setAttended(boolean isAttended) {
        this.isAttended = isAttended;
    }

    public Calendar getAttentionDate() {
        return attentionDate;
    }

    public void setAttentionDate(Calendar attentionDate) {
        this.attentionDate = attentionDate;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public List<Area> getLstAreas() {
        return lstAreas;
    }

    public void setLstAreas(List<Area> lstAreas) {
        this.lstAreas = lstAreas;
    }

    public List<UploadFileGeneric> getLstEvidences() {
        return lstEvidences;
    }

    public void setLstEvidences(List<UploadFileGeneric> lstEvidences) {
        this.lstEvidences = lstEvidences;
    }

    public List<Extension> getLstExtension() {
        return lstExtension;
    }

    public void setLstExtension(List<Extension> lstExtension) {
        this.lstExtension = lstExtension;
    }

    public User getAttentionUser() {
        return attentionUser;
    }

    public void setAttentionUser(User attentionUser) {
        this.attentionUser = attentionUser;
    }

    public String getAttentionComment() {
        return attentionComment;
    }

    public void setAttentionComment(String attentionComment) {
        this.attentionComment = attentionComment;
    }

    public Calendar getInitDate() {
        return initDate;
    }

    public void setInitDate(Calendar initDate) {
        this.initDate = initDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void merge(RequestDto requestDto, AttentionDto attentionDto, User user) {

        if (requestDto != null) {
            number = requestDto.getNumber();
            description = requestDto.getDescription();
        }

        if (attentionDto != null) {
            isAttended = true;
            attentionComment = attentionDto.getAttentionComment();
            attentionDate = Calendar.getInstance();
            attentionUser = user;
        }
    }
}
