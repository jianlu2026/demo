package com.example.demo.dto;

public class GreetingResponse {

    private Long id;
    private String message;

    public GreetingResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
