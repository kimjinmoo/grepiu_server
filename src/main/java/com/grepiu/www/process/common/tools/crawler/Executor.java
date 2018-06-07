package com.grepiu.www.process.common.tools.crawler;

import com.grepiu.www.process.common.tools.crawler.node.BaseNode;

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
