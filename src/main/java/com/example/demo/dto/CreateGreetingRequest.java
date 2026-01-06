package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateGreetingRequest {

    @NotBlank(message = "message must not be blank")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
