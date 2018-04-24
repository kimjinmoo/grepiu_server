package com.iuom.springboot.test.process.common;

import com.iuom.springboot.common.crawler.CrawlerHelper;
import com.iuom.springboot.common.crawler.domain.Cinema;
import com.iuom.springboot.common.crawler.domain.CinemaLocation;
import com.iuom.springboot.common.crawler.node.LotteCinemaLocationNode;
import com.iuom.springboot.common.crawler.node.LotteCinemaNode;
import com.iuom.springboot.process.sample.domain.TestMongoDBCrawler;
import com.iuom.springboot.test.process.config.LocalBaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class Crawling extends LocalBaseConfig {

    @Autowired
    private TestMongoDBCrawler mongoDBCrawler;

    @Override
    public void setUp() {

    }

    /**
     *
     * 예매 사이트 데이터 가져오기
     * @throws Exception
     */
    @Test
    public void reg() throws Exception {
        CrawlerHelper<Cinema> crawler = new CrawlerHelper<>();
        crawler.addNode(new LotteCinemaNode());
        crawler.addObserver(o -> {
            //DB delete
            mongoDBCrawler.deleteAll();
            //DB Insert
            o.parallelStream().forEach(v -> {
                mongoDBCrawler.insert(v);
            });
        });
        crawler.execute();
        log.debug("lotte : {}", crawler.getData());
    }

    /**
     *
     * 롯데 시네마 위치 정보 수집
     *
     * @throws Exception
     */
    @Test
    public void lotteLocationSearch() throws Exception {
        CrawlerHelper<CinemaLocation> crawlerHelper = new CrawlerHelper<>();
        crawlerHelper.addNode(new LotteCinemaLocationNode());
        crawlerHelper.execute();
    }

    /**
     *
     * 저장한 값을 가져온다.
     *
     * @throws Exception
     */
    @Test
    public void view() throws Exception {
        log.info("{}", mongoDBCrawler.findAll());
    }
}
