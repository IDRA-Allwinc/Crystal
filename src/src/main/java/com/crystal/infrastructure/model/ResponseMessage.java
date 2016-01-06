package com.crystal.infrastructure.model;

public class ResponseMessage {

    private boolean hasError;
    private boolean hasToLogout;
    private String message;
    private String urlToGo;
    private String title;
    private Object returnData;

    public ResponseMessage(){
    }

    public ResponseMessage(boolean hasError, String message){
        this.hasError = hasError;
        this.message = message;
    }

    public ResponseMessage(boolean hasError, String message, String title) {
        this.hasError = hasError;
        this.message = message;
        this.title = title;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlToGo() {
        return urlToGo;
    }

    public void setUrlToGo(String urlToGo) {
        this.urlToGo = urlToGo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }

    public boolean isHasToLogout() {
        return hasToLogout;
    }

    public void setHasToLogout(boolean hasToLogout) {
        this.hasToLogout = hasToLogout;
    }
}
