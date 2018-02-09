package com.iuom.springboot.process.sample.web;

import com.iuom.springboot.process.sample.domain.Greeting;
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

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting gretting(SampleMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello," + message.getName() + "!");
    }
}
