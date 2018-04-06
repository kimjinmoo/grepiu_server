package com.iuom.springboot.common.task;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 멀티 ParallelTask 유틸이다.
 *
 */
public class ParallelTaskHelper {

    // ParallelTask 저장소
    private List<ParallelTask> taskLists;

    /**
     *
     * Task 생성자
     *
     */
    public ParallelTaskHelper() {
        this.taskLists = Lists.newArrayList();
    }

    /**
     *
     * Task Size를 가져온다.
     *
     * @return
     */
    public int getTaskSize(){
        return this.taskLists.size();
    }
    /**
     *
     * Task를 추가한다.
     *
     */
    public void addTask(ParallelTask task) {
        taskLists.add(task);
    }

    /**
     *
     * Task를 추가한다.
     *
     * @param tasks ParallelTask 객체
     */
    public void addTask(ParallelTask...tasks) {
        for(ParallelTask t : tasks) {
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
     * @return
     * @throws Exception
     */
    public Map<String, Object> run(HashMap<String, Object> params) {
        Map<String, Object> r = Maps.newConcurrentMap();
        taskLists.parallelStream().forEach(task -> {
            r.putAll(task.run(params));
        });
        return r;
    }
}
