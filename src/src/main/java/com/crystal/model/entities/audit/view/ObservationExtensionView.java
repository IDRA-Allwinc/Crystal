package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("SELECT e.id_extension, c.id_observation observationId, c.is_attended isAttended, uf.id_upload_file_generic fileId, uf.file_name fileName, concat(substring(e.comment, 1,30),'...') extensionComment, date_format(e.end_date, '%d/%m/%Y') endDate "+ //, Addition.extensionId2 lastExtensionId " +
        "        FROM Observation c  " +
        "        INNER JOIN observation_extension_rel oec  ON c.id_observation = oec.id_observation  " +
        "        INNER JOIN extension e ON oec.id_extension= e.id_extension  " +
        "        INNER JOIN upload_file_generic uf ON uf.id_upload_file_generic = e.id_upload_file_generic  " +
//        "        INNER JOIN (select e2.id_extension extensionId2, oer2.id_observation observationId2 from " +
//        "                    Extension e2 " +
//        "                    INNER JOIN observation_extension_rel oer2  ON e2.id_extension = oer2.id_extension " +
//        "                    where e2.is_obsolete = false order by e2.id_extension desc limit 1 ) Addition on Addition.observationId2 = c.id_observation " +
        "        where   e.is_obsolete = false and e.is_initial = false and c.is_obsolete = false order by e.id_extension asc")
public class ObservationExtensionView {

    @Id
    @Column(name = "id_extension")
    private Long id;
    private Long observationId;
    private boolean isAttended;
    private String fileId;
    private String fileName;
    private String extensionComment;
    private String endDate;
//    private String lastExtensionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObservationId() {
        return observationId;
    }

    public void setObservationId(Long observationId) {
        this.observationId = observationId;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public void setAttended(boolean isAttended) {
        this.isAttended = isAttended;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

//    public String getLastExtensionId() {
//        return lastExtensionId;
//    }
//
//    public void setLastExtensionId(String lastExtensionId) {
//        this.lastExtensionId = lastExtensionId;
//    }
}