package com.crystal.model.entities.account;

import javax.persistence.*;
import java.util.Calendar;

@MappedSuperclass
public class UserAuditInfo {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_user_ins",nullable = false)
    private User userIns;

    @Column(name="date_time_ins", nullable = false)
    private Calendar calIns;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_user_upd",nullable = true)
    private User userUpd;

    @Column(name="date_time_upd", nullable = true)
    private Calendar calUpd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_user_del",nullable = true)
    private User userDel;

    @Column(name="date_time_del", nullable = true)
    private Calendar calDel;

    public User getUserIns() {
        return userIns;
    }

    public void setUserIns(User userIns) {
        this.userIns = userIns;
    }

    public Calendar getCalIns() {
        return calIns;
    }

    public void setCalIns(Calendar calIns) {
        this.calIns = calIns;
    }

    public User getUserUpd() {
        return userUpd;
    }

    public void setUserUpd(User userUpd) {
        this.userUpd = userUpd;
    }

    public Calendar getCalUpd() {
        return calUpd;
    }

    public void setCalUpd(Calendar calUpd) {
        this.calUpd = calUpd;
    }

    public User getUserDel() {
        return userDel;
    }

    public void setUserDel(User userDel) {
        this.userDel = userDel;
    }

    public Calendar getCalDel() {
        return calDel;
    }

    public void setCalDel(Calendar calDel) {
        this.calDel = calDel;
    }
}
