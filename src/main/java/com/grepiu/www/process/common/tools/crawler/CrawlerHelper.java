package com.grepiu.www.process.common.tools.crawler;

import com.google.common.collect.Lists;
import com.grepiu.www.process.common.tools.crawler.node.BaseNode;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 크롤러 유틸
 *
 * auth JM
 *
 */
@Slf4j
public class CrawlerHelper<T> implements Executor<T> {

    private List<Observer<T>> observers;     // 처리내역
    private List<BaseNode<T>> executeNodes;  // 실행 노드들
    private List<T> data;                     // data

    /**
     *
     * 기본 생성자
     * 초기화 한다.
     *
     */
    public CrawlerHelper() {
        this.observers = Lists.newArrayList();
        this.executeNodes = Lists.newArrayList();
        this.data = Lists.newArrayList();
    }

    /**
     *
     * 감시자를 추가한다.
     * 프로세스가 처리되면 감시자의 update 문이 처리된다.
     *
     * @param observer
     */
    @Override
    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    /**
     *
     * 감시자를 제거한다.
     *
     * @param observer
     */
    @Override
    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    /**
     *
     * 크롤링에 등록된 이벤드 들이
     * 완료 후 update를 처리한다.
     *
     */
    @Override
    public void callObserver() {
        observers.forEach(v->{ v.update(this.data);});
    }

    /**
     *
     * 크롤링 Node를 추가한다.
     *
     * @param node BaseNode 크롤링 로직 처리 노드
     */
    @Override
    public void addExecuteNode(BaseNode<T> node) {
        executeNodes.add(node);
    }

    /**
     *
     * 크롤링 Node를 병렬로 실행한다.
     *
     */
    @Override
    public void execute() throws Exception {
        try {
            executeNodes.parallelStream().forEach(v->{
                data.addAll(v.executeLogic());
            });
            // 처리가 완료 되면 등록된 감시자의 update문을 실행한다.
            callObserver();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * 처리 완료된 데이터를 가져온다.
     *
     * @return
     */
    public List<T> getData() {
        return data;
    }
}
