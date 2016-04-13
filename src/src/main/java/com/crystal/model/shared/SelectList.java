package com.crystal.model.shared;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SelectList {
    private Long id;
    private Integer idAux;
    private Calendar calendar;
    private String calendarStr;
    private String name;
    private String description;
    private String code;
    private Boolean lock;
    private Boolean isSelected;
    private Boolean specification;
    private String strDate;
    private String logType;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private Long value;
    private String subName;

    private String responsible;
    private String email;
    private String phone;


    public SelectList() {
    }

    public SelectList(Long id, String name) {
        this.id = id;
        this.name = name;
        this.value = 0L;
    }

    public SelectList(Long id, Boolean lock) {
        this.id = id;
        this.lock = lock;
    }

    public SelectList(Long id, Boolean isSelected,String name) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }

    public SelectList(Integer id, String description) {
        this.idAux = id;
        this.description = description;
    }

    public SelectList(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public SelectList(Long id, String name, String description, Boolean isSelected) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isSelected = isSelected;
    }

    public SelectList(Integer id, Calendar calendar) {
        this.idAux = id;
        this.calendar = calendar;
    }

    public SelectList(Long id, String name, String description, String secDescription) {
        this.id = id;
        this.name = description + " / " + name;
        this.description = secDescription;
    }

    public SelectList(String code, String name, Long id) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public SelectList(Long id, String name, String responsible, String email, String phone) {
        this.id = id;
        this.name = name;
        this.responsible = responsible;
        this.email = email;
        this.phone = phone;
    }

    //para listar en el detalle las obs, reconmen, etc, sin atender
    public SelectList(String name, String description, Calendar limitDate) {
        this.name = name;
        this.description= description;
        this.calendarStr = sdf.format(limitDate.getTime());
    }


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdAux() {
        return idAux;
    }

    public void setIdAux(Integer idAux) {
        this.idAux = idAux;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public Boolean getSpecification() {
        return specification;
    }

    public void setSpecification(Boolean specification) {
        this.specification = specification;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SelectList that = (SelectList) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
