package com.iuom.springboot.process.sample.controller;

import com.iuom.springboot.process.sample.domain.ChatMessages;
import com.iuom.springboot.process.sample.domain.SampleMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 *
 * Web소켓 샘플 컨트롤러이다.
 * Ref : https://spring.io/guides/gs/messaging-stomp-websocket/
 *
 */
@Controller
public class SampleSocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessages greeting(SampleMessage message) throws Exception {
        Thread.sleep(500);
        return new ChatMessages(message.getContent());
    }
}
