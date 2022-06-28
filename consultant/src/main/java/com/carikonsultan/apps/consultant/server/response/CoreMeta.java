package com.carikonsultan.apps.consultant.server.response;

import com.google.gson.annotations.SerializedName;

public class CoreMeta {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int code;

    public CoreMeta() {

    }

    public CoreMeta(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public CoreMeta(boolean status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public boolean isSuccess(){
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
