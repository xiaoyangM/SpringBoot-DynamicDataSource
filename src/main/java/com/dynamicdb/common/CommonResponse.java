package com.dynamicdb.common;

public class CommonResponse {

    public static final Integer SUCCESSCODE = 200;
    public static final Integer ERROERCODE = 404;
    public Object attachment;
    public int status;
    public String message;

    public CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public CommonResponse(Object attachment, int status, String message) {
        this.attachment = attachment;
        this.status = status;
        this.message = message;
    }

    public CommonResponse(Object attachment, int status) {
        this.attachment = attachment;
        this.status = status;
    }

    public static CommonResponse success(Object object) {
        return new CommonResponse(object, SUCCESSCODE);
    }

    public static CommonResponse error(Object object) {
        return new CommonResponse(object, ERROERCODE);
    }



    public String toString() {
        return "RespEntity{attachment=" + this.attachment + ", status=" + this.status + ", message='" + this.message + '\'' + '}';
    }

    public CommonResponse() {
    }

    public Object getAttachment() {
        return this.attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
