package com.grepiu.www.process.sample.controller;

import com.google.gson.Gson;
import com.grepiu.www.process.sample.domain.ChatMessages;
import com.grepiu.www.process.sample.domain.SampleMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

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
    public Object greeting(SampleMessage message) throws Exception {
        Thread.sleep(500);
        return message;
    }
}
