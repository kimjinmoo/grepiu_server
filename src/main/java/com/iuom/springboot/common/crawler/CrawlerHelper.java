package com.iuom.springboot.common.crawler;

import com.google.common.collect.Lists;
import com.iuom.springboot.common.crawler.domain.Cinema;
import com.iuom.springboot.common.crawler.node.BaseNode;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 크롤러 유틸
 *
 * auth JM
 */
@Slf4j
public class CrawlerHelper implements Executor {

    private List<Observer> observers; //처리내역
    private List<BaseNode> executeNodes; // 실행 노드들
    private List<Cinema> data;       // data


    public CrawlerHelper() {
        this.observers = Lists.newArrayList();
        this.executeNodes = Lists.newArrayList();
        this.data = Lists.newArrayList();
    }

    /**
     *
     * 감시자를 추가한다. 완료되면 해당 서버로 정보를 업데이트 한다.
     *
     * @param observer
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     *
     * 감시자를 제거한다.
     *
     * @param observer
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     *
     * 최종 완료 이벤트를 처리한다.
     *
     */
    @Override
    public void notifyObserver() {
        observers.forEach(v->{ v.update(data);});
    }

    /**
     *
     * 크롤링 Node를 추가한다.
     *
     * @param node
     */
    @Override
    public void addNode(BaseNode node) {
        executeNodes.add(node);
    }

    /**
     *
     * 크롤링 Node를 실행한다.
     *
     */
    @Override
    public void execute() {
        executeNodes.parallelStream().forEach(v->{
            data.addAll(v.executeLogic());
        });
        notifyObserver();
    }

    /**
     *
     * 처리 완료된 데이터를 가져온다.
     *
     * @return
     */
    public List<Cinema> getData() {
        return data;
    }
}
