package com.grepiu.www.process.common.api.domain;

public class ChatMessages {

    private String content;

    public ChatMessages() {}
    public ChatMessages(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }
}
