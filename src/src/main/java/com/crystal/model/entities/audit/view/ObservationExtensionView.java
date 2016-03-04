package com.crystal.model.entities.audit.view;

import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("SELECT e.id_extension, r.id_observation observationId, r.is_attended isAttended, uf.id_upload_file_generic fileId, uf.file_name fileName, concat(substring(e.comment, 1,30),'...') extensionComment, concat('', date(adddate(e.end_date, 0))) endDate, Addition.extensionId2 lastExtensionId " +
        "        FROM Observation r  " +
        "        INNER JOIN observation_extension_rel oer  ON r.id_observation = oer.id_observation  " +
        "        INNER JOIN extension e ON oer.id_extension= e.id_extension  " +
        "        INNER JOIN upload_file_generic uf ON uf.id_upload_file_generic = e.id_upload_file_generic  " +
        "        INNER JOIN (select e2.id_extension extensionId2, oer2.id_observation observationId2 from " +
        "                    Extension e2 " +
        "                    INNER JOIN observation_extension_rel oer2  ON e2.id_extension = oer2.id_extension " +
        "                    where e2.is_obsolete = false order by e2.id_extension desc limit 1 ) Addition on Addition.observationId2 = r.id_observation " +
        "        where e.is_obsolete = false and r.is_obsolete = false order by e.id_extension asc")
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
    private String lastExtensionId;

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

    public String getLastExtensionId() {
        return lastExtensionId;
    }

    public void setLastExtensionId(String lastExtensionId) {
        this.lastExtensionId = lastExtensionId;
    }
}