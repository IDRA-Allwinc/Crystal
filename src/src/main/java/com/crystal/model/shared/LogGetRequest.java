package com.crystal.model.shared;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "log_get_request")
public class LogGetRequest {
    @Id
    @GeneratedValue
    @Column(name = "id_get_request_log")
    private Long id;

    @Column(name = "username", length = 255, nullable = true)
    private String username;

    @Column(name = "host_address", length = 255, nullable = false)
    private String hostAddress;

    @Column(name = "requested_resource", length = 1000, nullable = false)
    private String resource;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "requested_data", nullable = false)
    private String requestedData;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "header_data", nullable = false)
    private String headerData;

    @Column(name = "register_date", nullable = false)
    private Calendar registerDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getRequestedData() {
        return requestedData;
    }

    public void setRequestedData(String requestedData) {
        this.requestedData = requestedData;
    }

    public String getHeaderData() {
        return headerData;
    }

    public void setHeaderData(String headerData) {
        this.headerData = headerData;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }
}
