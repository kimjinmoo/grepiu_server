package com.iuom.springboot.process.sample.task;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TaskGroup {

    @Bean
    public Task getDate(){
        return new Task() {
            @Override
            public void run() throws Exception {
            }
        };
    }
}
