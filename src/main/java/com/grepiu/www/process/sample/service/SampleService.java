package com.grepiu.www.process.sample.service;

import java.util.List;

/**
 * SampleService
 *
 */
public interface SampleService {

    // 샘플 List 가져오기
    public List<String> getSampleList();

    // 시네마 로케이션 수집
    public void collectionCinemaLocation();

    // 영화 정보 수집
    public void collectionCinemaMovieInfo();
}
