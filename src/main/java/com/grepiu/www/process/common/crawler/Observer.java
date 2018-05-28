package com.grepiu.www.process.common.crawler;

import java.util.List;

/**
 *
 * 옵저버등록, add 후 처리 이벤트 등록
 *
 */
public interface Observer<T> {
    public void update(List<T> obj);
}
