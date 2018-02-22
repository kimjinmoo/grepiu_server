package com.iuom.springboot.process.sample.service;

import com.iuom.springboot.common.crawler.node.LotteCinemaNode;
import com.iuom.springboot.common.job.Task;
import com.iuom.springboot.common.job.TaskHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Autowired
    private Task crawlerLotteCinema;

    /**
     *
     * 테스트크를 병렬 처리한다.
     *
     */
    public HashMap<String, Object> process(Map<String, Object> params){
        TaskHelper taskHelper = new TaskHelper();
        try {
            taskHelper.addTask(getSampleTask1);
            taskHelper.addTask(getSampleTask2);

            taskHelper.run(params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskHelper.getReturnMaps();
    }

    /**
     *
     * 크롤링테스트 - 비동기처리
     *
     */
    public Object lotteRun() {
        return new LotteCinemaNode().executeLogic();
    }
}
