package com.iuom.springboot.process.api.task;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TaskService {

    private Task getDate;

    private List<Task> taskList;

    @PostConstruct
    void setUp() {
        taskList = Lists.newArrayList();
        taskList.add(getDate);
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
