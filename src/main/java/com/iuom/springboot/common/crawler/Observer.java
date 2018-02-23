package com.iuom.springboot.common.crawler;

/**
 *
 * 옵저버등록, add 후 처리 이벤트 등록
 *
 */
public interface Observer {
    public void update(Object obj);
}
