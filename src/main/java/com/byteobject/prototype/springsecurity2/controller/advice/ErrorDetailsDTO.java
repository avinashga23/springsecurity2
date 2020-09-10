package com.byteobject.prototype.springsecurity2.controller.advice;

import java.util.Date;

public class ErrorDetailsDTO {

    private Date timestamp;

    private String error;

    private String message;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
