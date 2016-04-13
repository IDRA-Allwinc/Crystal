package com.crystal.model.shared;

public class ReportViewDto {

    private String id;

    private int auditNumber;

    private int emitted;

    private int attended;

    private int recommendations;

    private int observations;

    private int responsibilities;

    private int notAttended;

    private Float progress;

    private Long aux;

    public ReportViewDto(String id, int auditNumber, int emitted, int attended, int recommendations, int observations, int responsibilities, int notAttended, Float progress) {
        this.id = id;
        this.auditNumber = auditNumber;
        this.emitted = emitted;
        this.attended = attended;
        this.recommendations = recommendations;
        this.observations = observations;
        this.responsibilities = responsibilities;
        this.notAttended = notAttended;
        this.progress = progress;
    }


    public ReportViewDto(String id, int auditNumber, int emitted, int attended, int recommendations, int observations, int responsibilities, int notAttended, Float progress, Long aux) {
        this.id = id;
        this.auditNumber = auditNumber;
        this.emitted = emitted;
        this.attended = attended;
        this.recommendations = recommendations;
        this.observations = observations;
        this.responsibilities = responsibilities;
        this.notAttended = notAttended;
        this.progress = progress;
        this.aux = aux;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAuditNumber() {
        return auditNumber;
    }

    public void setAuditNumber(int auditNumber) {
        this.auditNumber = auditNumber;
    }

    public int getEmitted() {
        return emitted;
    }

    public void setEmitted(int emitted) {
        this.emitted = emitted;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public int getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(int recommendations) {
        this.recommendations = recommendations;
    }

    public int getObservations() {
        return observations;
    }

    public void setObservations(int observations) {
        this.observations = observations;
    }

    public int getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(int responsibilities) {
        this.responsibilities = responsibilities;
    }

    public int getNotAttended() {
        return notAttended;
    }

    public void setNotAttended(int notAttended) {
        this.notAttended = notAttended;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }

    public Long getAux() {
        return aux;
    }

    public void setAux(Long aux) {
        this.aux = aux;
    }
}
