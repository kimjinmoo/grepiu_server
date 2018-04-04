package com.iuom.springboot.process.sample.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iuom.springboot.common.task.ParallelTask;
import com.iuom.springboot.common.task.ParallelTaskHelper;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 *
 * 샘플 병렬 처리 VoidTask 이다.
 *
 */
@Service
@Slf4j
public class SampleTaskService {

    @Autowired
    private ParallelTask getSampleTask1;

    @Autowired
    private ParallelTask getSampleTask2;


    /**
     *
     * 테스트크를 병렬 처리한다.
     *
     */
    public HashMap<String, Object> process(HashMap<String, Object> params){
        ParallelTaskHelper taskHelper = new ParallelTaskHelper();
        taskHelper.addTask(getSampleTask1,getSampleTask2);

        return taskHelper.run(params);
    }

    @Bean
    public ParallelTask getSampleTask1(){
        return (HashMap<String, Object> params) -> {
            log.debug("getSampleTask1 ParallelTask");
            HashMap<String, Object> result = Maps.newHashMap();

            List<Integer> job = Lists.newArrayList();
            IntStream.range(0,30).forEach(i->{
                log.info("getSampleTask1 {} : ", i);
                job.add(i);
            });
            result.put("task1", job);

            return result;
        };
    }

    @Bean
    public ParallelTask getSampleTask2(){
        return (HashMap<String, Object> params) -> {
            HashMap<String, Object> result = Maps.newHashMap();

            List<Integer> job = Lists.newArrayList();
            IntStream.range(0,25).forEach(i->{
                log.info("getSampleTask2 {} : ", i);
                job.add(i);
            });
            result.put("task2", job);
            return result;
        };
    }
}
