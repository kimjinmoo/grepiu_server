package com.iuom.springboot.process.sample.service;

import com.google.common.collect.Lists;
import com.iuom.springboot.process.sample.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 * 샘플 병렬 처리 Task 이다.
 *
 */
@Service
public class SampleTaskService {

    @Autowired
    private Task getDate;

    @Autowired
    private Task getWeather;

    private List<Task> taskList;

    @PostConstruct
    void setUp() {
        taskList = Lists.newArrayList();
        taskList.add(getDate);
        taskList.add(getWeather);
    }

    public void process(){
        taskList.parallelStream().forEach(task->{
            try {
                task.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
