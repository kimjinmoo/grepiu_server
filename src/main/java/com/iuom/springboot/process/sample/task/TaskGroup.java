package com.iuom.springboot.process.sample.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * 테스크 그룹, 작업 Bean을 등록한다.
 *
 */
@Slf4j
@Component
public class TaskGroup {

    @Bean
    public Task getDate(){
        return new Task() {
            @Override
            public void run() throws Exception {
                log.debug("getData Task");
            }
        };
    }

    @Bean
    public Task getWeather(){
        return new Task() {
            @Override
            public void run() throws Exception {
                log.debug("getWeather Task");
            }
        };
    }
}
