package com.iuom.springboot.common.job;

import com.iuom.springboot.common.crawler.CrawlerHelper;
import com.iuom.springboot.common.crawler.Observer;
import com.iuom.springboot.common.crawler.domain.Cinema;
import com.iuom.springboot.common.crawler.node.LotteCinemaNode;
import com.iuom.springboot.process.sample.domain.SampleMessage;
import com.iuom.springboot.process.sample.domain.TestMongoDBCrawler;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 스케쥴 Job
 */
@Slf4j
@Component
public class ScheduleJob {

  @Autowired
  private TestMongoDBCrawler mongoDBCrawler;

  @Autowired
  private SimpMessagingTemplate template;

  /**
   * 크롤링 스케쥴러
   *
   * 6시간기준으로 갱신
   */
  @Scheduled(fixedDelay = 1000 * 60 * 60 * 6)
  public void crawler() {
    log.info(" start crawler=======================");
    //step1. Collect Data
    CrawlerHelper ch = new CrawlerHelper();
    ch.addNode(new LotteCinemaNode());
    ch.addObserver(new Observer() {
      @Override
      public void update(Object obj) {
        //완료 후 최종 이벤트 처리
        template.convertAndSend("/topic/messages", new SampleMessage("시스템 알림", "크롤링 처리 완료 신규 데이터를 확인하세요."));
      }
    });
    ch.execute();

    //step2. data set
    List<Cinema> data = ch.getData();

    //step3. DB delete
    mongoDBCrawler.deleteAll();

    //step4 DB insert
    data.parallelStream().forEach(v -> {
      mongoDBCrawler.insert(v);
    });
    log.info(" finished crawler=======================");
  }
}
