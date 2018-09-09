package com.grepiu.www.process.common.job;

import com.grepiu.www.process.common.utils.DateUtils;
import com.grepiu.www.process.common.tools.crawler.CrawlerHelper;
import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import com.grepiu.www.process.common.tools.crawler.node.LotteCinemaNode;
import com.grepiu.www.process.grepiu.dao.LotteCineDBRepository;
import com.grepiu.www.process.grepiu.dao.LotteCineLocalRepository;
import com.grepiu.www.process.common.api.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 스케쥴 Job
 *
 * cron 표현
 * 초 분 시간 일 월 요일(1 일요일- 7 토요일 OR MON,SUN) 년도(Optional)
 *
 * * : 모든 수
 * ? : Day of Month, Day of Week 에서 사용 가능
 * - : 기간을 설정 ex) 1-2
 * , : 특정 시간을 설정
 * / : 증가를 표현 ex) 초 0/15 는 15초간격
 * L : Day of Month 에서만 사용 마지막일
 * W : Day of Month 에서만 사용 가능  가장 가까운 평일
 * # : Day Of Week 에서 사용 가능 ex) 1#3 3번째 주 일요일 실행
 */
@Slf4j
@Component
public class ScheduleJob {

  @Autowired
  private LotteCineDBRepository mongoDBCrawler;

  @Autowired
  private LotteCineLocalRepository lotteCineLocalRepository;

  @Autowired
  private SimpMessagingTemplate template;

  /**
   * 크롤링 스케쥴러
   * <second> <minute> <hour> <day-of-month> <month> <day-of-week> <year> <command>
   * 매일 07시,12,17시
   */
//  @Scheduled(fixedDelay = 1000*60*60*3)
  @Scheduled(cron="00 30 08,11,17 * * ?")
  public void crawler() throws Exception {
    log.info(" start crawler======================= {}", DateUtils.now("yyyy/MM/dd hh:mm:ss"));
    //step1. Collect Data
    CrawlerHelper<Cinema> ch = new CrawlerHelper<>();
    ch.addExecuteNode(new LotteCinemaNode());
    ch.addObserver(o -> {
      //DB delete
      mongoDBCrawler.deleteAll();
      //DB Insert
      o.parallelStream().forEach(v -> {
        mongoDBCrawler.insert(v);
      });
      //완료 후 최종 이벤트 처리
      template.convertAndSend("/topic/messages",
          new Message("시스템 알림", "크롤링 처리 완료 신규 데이터를 확인하세요."));
    });
    ch.execute();
    log.info(" finished crawler=======================");
  }
}
