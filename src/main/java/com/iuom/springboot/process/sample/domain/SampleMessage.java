package com.iuom.springboot.process.sample.domain;

public class SampleMessage {
    private String name;

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
}
