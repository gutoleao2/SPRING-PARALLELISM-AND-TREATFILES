package com.example.challenge.williams.federalrevenue.entrypoint.response;

import static com.example.challenge.williams.federalrevenue.util.FileUtil.folderPath;

public class DefaultResponse {

    private String message;
    private String detailsMessage;

    public DefaultResponse(String message, String detailsMessage) {
        this.message = message;
        this.detailsMessage = detailsMessage;
    }

    public DefaultResponse() {

    }

    public String getMessage() {
        return message;
    }

    public DefaultResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDetailsMessage() {
        return detailsMessage;
    }

    public DefaultResponse setDetailsMessage(String detailsMessage) {
        this.detailsMessage = detailsMessage;
        return this;
    }
}
