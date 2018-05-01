package com.iuom.springboot.common.crawler;

import com.iuom.springboot.common.crawler.node.BaseNode;

/**
 *
 * 크롤링 실행자
 *
 */
public interface Executor<T> {

    public void addObserver(Observer<T> observer);
    public void removeObserver(Observer<T> observer);
    public void callObserver();
    public void addExecuteNode(BaseNode<T> node);
    public void execute() throws Exception;
}
