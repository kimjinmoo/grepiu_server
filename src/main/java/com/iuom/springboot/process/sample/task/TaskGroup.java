package com.iuom.springboot.process.sample.task;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
            public void run(Map<String, Object> returnMap, Map<String, Object> param) throws Exception {
                log.debug("getSampleTask1 Task");
                List<Integer> job = Lists.newArrayList();
                IntStream.range(0,1000).forEach(i->{
                    log.debug("getSampleTask1 {} : ", i);
                    job.add(i);
                });
                returnMap.put("getSampleTask1_jobCount", job.size());
            }
        };
    }

    @Bean
    public Task getSampleTask2(){
        return new Task() {
            @Override
            public void run(Map<String, Object> returnMap, Map<String, Object> param) throws Exception {
                log.debug("getSampleTask2 Task");
                List<Integer> job = Lists.newArrayList();
                IntStream.range(0,10000).forEach(i->{
                    log.debug("getSampleTask2 {} : ", i);
                    job.add(i);
                });
                returnMap.put("getSampleTask2_jobCount", job.size());
            }
        };
    }
}
