package com.iuom.springboot.process.sample.task;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iuom.springboot.common.crawler.node.LotteCinemaNode;
import com.iuom.springboot.common.job.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 *
 * 테스크 그룹, 작업 Bean을 등록한다.
 *
 */
@Slf4j
@Component
public class TaskGroup {

    @Bean
    public Task getSampleTask1(){
        return new Task() {
            @Override
            public HashMap<String,Object> run(Map<String, Object>...returnMap) {
                log.debug("getSampleTask1 Task");
                HashMap<String, Object> result = Maps.newHashMap();

                List<Integer> job = Lists.newArrayList();
                IntStream.range(0,100).forEach(i->{
                    log.error("getSampleTask1 {} : ", i);
                    job.add(i);
                });
                result.put("task1", job);
                return result;
            }
        };
    }

    @Bean
    public Task getSampleTask2(){
        return new Task() {
            @Override
            public HashMap<String,Object> run(Map<String, Object>...params) {
                log.debug("getSampleTask2 Task");
                HashMap<String, Object> result = Maps.newHashMap();

                List<Integer> job = Lists.newArrayList();
                IntStream.range(0,100).forEach(i->{
                    log.error("getSampleTask2 {} : ", i);
                    job.add(i);
                });
                result.put("task2", job);
                return result;
            }
        };
    }

    @Bean
    public Task crawlerLotteCinema() {
        return new Task() {
            @Override
            public HashMap<String, Object> run(Map<String, Object>... object) {
                HashMap<String, Object> result = Maps.newHashMap();
                result.put("jsonString", new LotteCinemaNode().executeLogic());
                return result;
            }
        };
    }
}
