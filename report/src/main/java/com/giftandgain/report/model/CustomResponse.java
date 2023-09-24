package com.giftandgain.report.model;

import java.util.List;

public class CustomResponse<T> {
    private String message;

    private List<T> payload;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getPayload() {
        return payload;
    }

    public void setPayload(List<T> payload) {
        this.payload = payload;
    }
}
