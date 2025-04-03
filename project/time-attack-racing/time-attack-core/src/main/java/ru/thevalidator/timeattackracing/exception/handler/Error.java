package ru.thevalidator.timeattackracing.exception.handler;

import java.time.Instant;

public class Error {

    private String errorCode;

    private String message;

    private Integer status;

    private String url = "Not available";

    private String method = "Not available";

    private Instant timestamp = Instant.now();

    public Error(Builder builder) {
        this.errorCode = builder.errorCode;
        this.message = builder.message;
        this.status = builder.status;
        this.url = builder.url;
        this.method = builder.method;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public static class Builder {

        private String errorCode;

        private String message;

        private Integer status;

        private String url = "Not available";

        private String method = "Not available";

        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Error build() {
            return new Error(this);
        }

    }

}
