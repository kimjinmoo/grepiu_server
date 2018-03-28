package com.iuom.springboot.common.crawler;

import com.iuom.springboot.common.crawler.node.BaseNode;

/**
 *
 * 크롤링 실행자
 *
 */
public interface Executor {

    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver();
    public void addNode(BaseNode node);
    public void execute();
}
