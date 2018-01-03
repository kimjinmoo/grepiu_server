package com.iuom.springboot.common.job;

import java.util.List;
import java.util.Map;

/**
 *
 * 멀티 Task 유틸이다.
 *
 */
public class TaskUtil {

    private List<Task> taskLists;

    /**
     *
     * Task에 Map을 가져온다.
     *
     * @param index
     * @param maps
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> Map<K,V> getMap(int index, Map<K,V>...maps) throws Exception {
        if(maps.length < index) {
            throw new Exception("length error");
        }
        return maps[index];
    }

//    public static List<Task> setTaskList(List<Task> tasks) {
//        taskLists =
//    }

    /**
     *
     * Task를 등록한다.
     *
     * @param taskList
     */
    public static List<Task> addTask(List<Task> taskList, Task task) {
        taskList.add(task);
        return taskList;
    }
}
