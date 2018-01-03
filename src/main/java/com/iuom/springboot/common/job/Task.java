package com.iuom.springboot.common.job;

import java.util.Map;

/**
 *
 *  처리후 결과값을 리턴한다.
 *
 */
public interface Task {
    // 맵에 return 처리
    void run(Map<String, Object>...object) throws Exception;
}
