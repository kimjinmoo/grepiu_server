package com.grepiu.www.process.api.controller;

import com.grepiu.www.process.api.domain.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class CommonController {

    /**
     *
     * Web소켓
     * Ref : https://spring.io/guides/gs/messaging-stomp-websocket/
     *
     */
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Object greeting(Message message) throws Exception {
        Thread.sleep(500);
        return message;
    }

}
