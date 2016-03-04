package com.crystal.model.entities.audit;

import com.crystal.model.entities.account.UserAuditInfo;
import com.crystal.model.entities.catalog.Area;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="notification")
public class Notification extends UserAuditInfo {

    @Id
    @GeneratedValue
    @Column(name = "id_notification")
    private Long id;

    @Column(name="title", length = 100, nullable = false)
    @NotEmpty(message="El título es un campo requerido")
    private String title;

    @Column(name="message", length = 2000, nullable = false)
    @NotEmpty(message="El asunto es un campo requerido")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audit", nullable = false)
    private Audit audit;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "notification_area_rel",
            joinColumns = {@JoinColumn(name = "id_notification", referencedColumnName = "id_notification")},
            inverseJoinColumns = {@JoinColumn(name = "id_area", referencedColumnName = "id_area")})
    private List<Area> lstAreas;

    @Column(name="is_obsolete", nullable = false)
    private boolean isObsolete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public List<Area> getLstAreas() {
        return lstAreas;
    }

    public void setLstAreas(List<Area> lstAreas) {
        this.lstAreas = lstAreas;
    }
}
