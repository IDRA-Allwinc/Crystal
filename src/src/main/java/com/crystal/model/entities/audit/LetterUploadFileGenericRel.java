package com.crystal.model.entities.audit;

import com.crystal.model.shared.UploadFileGeneric;

import javax.persistence.*;

@Entity
@Table(name="letter_upload_file_generic_rel")
public class LetterUploadFileGenericRel {

    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_letter_upload_file_generic_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_letter", nullable = false)
    private Letter letter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_upload_file_generic", nullable = false)
    private UploadFileGeneric uploadFileGeneric;

    @Column(name = "is_additional", nullable = false)
    private boolean isAdditional;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public UploadFileGeneric getUploadFileGeneric() {
        return uploadFileGeneric;
    }

    public void setUploadFileGeneric(UploadFileGeneric uploadFileGeneric) {
        this.uploadFileGeneric = uploadFileGeneric;
    }

    public boolean isAdditional() {
        return isAdditional;
    }

    public void setAdditional(boolean isAdditional) {
        this.isAdditional = isAdditional;
    }
}
