package com.crystal.model.shared;

import javax.persistence.*;

@Entity
@Table(name = "system_setting")
public class SystemSetting {
    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_system_setting")
    private Long id;

    @Column(name = "key_setting", length = 100, nullable = false)
    private String key;

    @Column(name = "value_setting", length = 2000, nullable = false)
    private String value;

    @Column(name = "description", length = 2000, nullable = false)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}