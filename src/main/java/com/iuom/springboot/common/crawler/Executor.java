package com.iuom.springboot.common.crawler;

/**
 *
 * 크롤링 실행자
 *
 */
public interface Executor {

    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver();
}
