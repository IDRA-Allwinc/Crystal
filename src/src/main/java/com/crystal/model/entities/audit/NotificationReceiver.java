package com.crystal.model.entities.audit;

import javax.persistence.*;

@Entity
@Table(name="notification_receiver")
public class NotificationReceiver {

    @Id
    @GeneratedValue
    @Column(name = "id_notification_receiver")
    private Long id;

    @Column(name="name", length = 200, nullable = false)
    private String name;

    @Column(name="phone", length = 200, nullable = false)
    private String phone;

    @Column(name="email", length = 200, nullable = false)
    private String email;

    @Column(name="belongs_to", length = 200, nullable = false)
    private String belongsTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_notification", nullable = false)
    private Notification notification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
