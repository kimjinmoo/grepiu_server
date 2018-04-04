package com.iuom.springboot.common.task;

import java.util.HashMap;

/**
 *
 *  처리후 결과값을 리턴한다.
 *
 */
public interface ParallelTask {
    /**
     *
     * 파람을 받아 처리한다.
     *
     * @param params Map 인터페이스 객체
     * @return Map 인터페이스 객체
     */
    public HashMap<String, Object> run(HashMap<String, Object> params);
}
