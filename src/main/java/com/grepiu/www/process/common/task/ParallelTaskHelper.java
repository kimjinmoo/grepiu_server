package com.grepiu.www.process.common.task;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 멀티 ParallelTask 유틸이다.
 *
 */
public class ParallelTaskHelper<T extends Map> {

    // 리턴 Maps
    private T resultMap;

    // ParallelTask 저장소
    private List<ParallelTask> taskLists;

    /**
     *
     * Task 생성자
     *
     * @param returnMaps 리턴할 Map을 초기화 한다
     */
    public ParallelTaskHelper(T returnMaps) {
        this.taskLists = Lists.newArrayList();
        this.resultMap = returnMaps;
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
     * @param params 파라메터
     * @return
     * @throws Exception
     */
    public void run(HashMap<String, Object> params) {
        taskLists.parallelStream().forEach(task -> {
            resultMap.putAll(task.run(params));
        });
    }

    /**
     *
     * 처리한 데이터를 가져온다.
     *
     * @return
     */
    public T getResultMap() {
        return resultMap;
    }
}
