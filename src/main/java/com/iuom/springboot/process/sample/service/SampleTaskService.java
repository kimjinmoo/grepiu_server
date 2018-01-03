package com.iuom.springboot.process.sample.service;

import com.google.common.collect.Lists;
import com.iuom.springboot.common.job.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 *
 * 샘플 병렬 처리 VoidTask 이다.
 *
 */
@Service
public class SampleTaskService {

    @Autowired
    private Task getSampleTask1;

    @Autowired
    private Task getSampleTask2;

    private List<Task> taskList;

    @PostConstruct
    void setUp() {
        taskList = Lists.newArrayList();
        taskList.add(getSampleTask1);
        taskList.add(getSampleTask2);
    }

    /**
     *
     * 테스트크를 병렬 처리한다.
     *
     */
    public void process(Map<String, Object> returnMap, Map<String, Object> params){
        taskList.parallelStream().forEach(task->{
            try {
                task.run(returnMap, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
