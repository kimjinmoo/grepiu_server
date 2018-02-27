package com.iuom.springboot.process.sample.service;

import java.util.List;

/**
 * SampleService
 *
 */
public interface SampleService {

    public List<String> getSampleList();


    /**
     *
     * 크롤링테스트 - 비동기처리
     *
     */
    public void lotteRun();
}
