package com.iuom.springboot.process.api.task;

import java.util.Map;

/**
 *
 * 병렬 처리 Task
 *
 */
public interface Task {
    void run() throws Exception;
}
