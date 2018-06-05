package com.grepiu.www.process.sample.domain;

/**
 *
 * 웹소켓 메세지 규격
 *
 */
public class SampleMessage {
    private String name;
    private String message;
    private String content;

    public SampleMessage() {}

    public SampleMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }

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

    public String getContent() {
        return this.name+" : "+this.message;
    }
}
