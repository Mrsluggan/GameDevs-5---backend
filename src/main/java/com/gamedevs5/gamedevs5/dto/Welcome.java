package com.gamedevs5.gamedevs5.dto;

import org.springframework.data.annotation.Id;

public class Welcome {

    @Id
    private String id;
    private String message;

    public Welcome() {

    }

    public Welcome(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
