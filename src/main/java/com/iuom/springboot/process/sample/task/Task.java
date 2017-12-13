package com.iuom.springboot.process.sample.task;

import java.util.Map;

/**
 *
 * 병렬 처리 Task
 *
 */
public interface Task {
    void run(Map<String, Object> returnMap, Map<String, Object> params) throws Exception;
}
