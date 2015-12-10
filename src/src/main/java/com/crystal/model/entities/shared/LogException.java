package com.crystal.model.entities.shared;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "log_exception")
public class LogException {
    @Id
    @GeneratedValue
    @Column(name = "id_log_exception")
    private Long id;

    @Column(name = "class_name", length = 500, nullable = false)
    private String className;

    @Column(name = "method_name", length = 1000, nullable = false)
    private String methodName;

    @Lob @Basic(fetch=FetchType.LAZY)
    @Column(name = "error_message", nullable = false)
    private String errorMessage;

    @Column(name = "username", length = 250, nullable = true)
    private String username;

    @Column(name = "datetime_error", nullable = false)
    private Calendar calendar;

    public LogException(String className, String methodName, String errorMessage, String username){
        this.className = className;
        this.methodName = methodName;
        this.errorMessage = errorMessage;
        this.username = username;
        calendar = Calendar.getInstance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
