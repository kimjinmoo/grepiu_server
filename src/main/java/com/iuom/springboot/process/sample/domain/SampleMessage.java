package com.iuom.springboot.process.sample.domain;

public class SampleMessage {
    private String name;
    private String message;

    public SampleMessage() {}

    public SampleMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
