package com.iuom.springboot.common.job;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 멀티 Task 유틸이다.
 *
 */
public class TaskHelper {

    // Task 저장소
    private List<Task> taskLists;

    // 처리 내용 결과 저장
    private HashMap<String, Object> returnMaps;
    /**
     *
     * Task를
     *
     */
    public TaskHelper() {
        this.taskLists = Lists.newArrayList();
        this.returnMaps = Maps.newHashMap();
    }

    /**
     *
     * Task를 추가한다.
     *
     */
    public void addTask(Task task) {
        taskLists.add(task);
    }

    /**
     *
     * Task를 추가한다.
     *
     * @param tasks Task 객체
     */
    public void addTask(Task...tasks) {
        for(Task t : tasks) {
            taskLists.add(t);
        }
    }

    /**
     *
     * task를 초기화 한다
     *
     */
    public void clearTask() {
        taskLists.clear();;
    }

    /**
     *
     * Task를 실행한다.
     *
     * @param params
     */
    public void run(Map<String, Object>...params) throws Exception {
        taskLists.parallelStream().forEach(task->{
            HashMap<String, Object> result =  task.run(params);
            returnMaps.putAll(result);
        });
    }

    /**
     *
     * Task 결과값을 가져온다.
     *
     * @return
     */
    public HashMap<String, Object> getReturnMaps() {
        return returnMaps;
    }
}
