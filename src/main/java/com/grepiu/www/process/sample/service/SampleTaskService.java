package com.grepiu.www.process.sample.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.grepiu.www.process.common.tools.task.ParallelTask;
import com.grepiu.www.process.common.tools.task.ParallelTaskHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final ParallelTask getSampleTask1;

    private final ParallelTask getSampleTask2;

    public SampleTaskService(ParallelTask getSampleTask1,
        ParallelTask getSampleTask2) {
        this.getSampleTask1 = getSampleTask1;
        this.getSampleTask2 = getSampleTask2;
    }

    /**
     *
     * 테스트크를 병렬 처리한다.
     *
     */
    public Map<String, Object> process(HashMap<String, Object> params){
        ParallelTaskHelper<HashMap<String, Object>> taskHelper = new ParallelTaskHelper(Maps.newHashMap());
        taskHelper.addTask(getSampleTask1,getSampleTask2);
        taskHelper.run(params);
        return taskHelper.getResultMap();
    }
}
