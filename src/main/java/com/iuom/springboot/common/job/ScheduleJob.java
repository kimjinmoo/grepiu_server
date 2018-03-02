package com.iuom.springboot.common.job;

import com.google.gson.Gson;
import com.iuom.springboot.common.crawler.CrawlerHelper;
import com.iuom.springboot.common.crawler.domain.Cinema;
import com.iuom.springboot.common.crawler.node.LotteCinemaNode;
import com.iuom.springboot.process.sample.domain.TestMongoDBCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 *
 * 스케쥴 Job
 *
 */
@Slf4j
@Component
public class ScheduleJob {

    @Autowired
    private TestMongoDBCrawler mongoDBCrawler;

    /**
     *
     * 크롤링 스케쥴러
     *
     */
    @Scheduled(fixedDelay = 1000*60*60)
    public void crawler() {
        log.info(" start crawler=======================");
        //step1. DB delete
        mongoDBCrawler.deleteAll();

        //step2. Collect Data
        CrawlerHelper ch = new CrawlerHelper();
        ch.addNode(new LotteCinemaNode());
        ch.execute();

        //step3. data insert
        List<Cinema> data = ch.getData();
        data.forEach(v->{
            mongoDBCrawler.insert(v);
        });
        log.info("insert Data  : {} ", new Gson().toJson(data));
        log.info(" finished crawler=======================");
    }
}
