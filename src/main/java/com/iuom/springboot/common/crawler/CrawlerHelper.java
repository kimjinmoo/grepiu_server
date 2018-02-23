package com.iuom.springboot.common.crawler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iuom.springboot.common.crawler.node.BaseNode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * 크롤러 유틸
 *
 */
@Slf4j
public class CrawlerHelper implements Executor{

    private ArrayList<Observer> observers; //처리내역
    private ArrayList<BaseNode> executeNodes; // 실행 노드들
    private HashMap<String, Object> data;       // data


    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        observers.forEach(v->{ v.update(data);});
    }

    @Override
    public void addNode(BaseNode node) {
        executeNodes.add(node);
    }

    @Override
    public void execute() {
        executeNodes.parallelStream().forEach(v->{
            data.put(UUID.randomUUID().toString(),v.executeLogic());
        });
        notifyObserver();
    }

    public CrawlerHelper() {
        this.observers = Lists.newArrayList();
        this.executeNodes = Lists.newArrayList();
        this.data = Maps.newHashMap();
    }

    public HashMap<String, Object> getData() {
        return data;
    }
}
