package com.iuom.springboot.common.crawler;

import com.iuom.springboot.common.crawler.domain.Cinema;
import java.util.List;

/**
 *
 * 옵저버등록, add 후 처리 이벤트 등록
 *
 */
public interface Observer {
    public void update(List<Cinema> obj);
}
