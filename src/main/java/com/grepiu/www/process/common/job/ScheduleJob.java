package com.grepiu.www.process.common.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepiu.www.process.common.tools.crawler.domain.CinemaLocation;
import com.grepiu.www.process.common.tools.domain.MapGoogleResultGeometryVO;
import com.grepiu.www.process.common.utils.DateUtil;
import com.grepiu.www.process.common.tools.crawler.CrawlerHelper;
import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import com.grepiu.www.process.common.tools.crawler.node.LotteCinemaNode;
import com.grepiu.www.process.common.utils.MapUtil;
import com.grepiu.www.process.sample.dao.LotteCineDBRepository;
import com.grepiu.www.process.sample.dao.LotteCineLocalRepository;
import com.grepiu.www.process.sample.domain.SampleMessage;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
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
    log.info(" start crawler======================= {}", DateUtil.now("yyyy/MM/dd hh:mm:ss"));
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
          new SampleMessage("시스템 알림", "크롤링 처리 완료 신규 데이터를 확인하세요."));
    });
    ch.execute();
    log.info(" finished crawler=======================");
  }

  /**
   *
   * 롯데 시네마 위치 정보 Set 새벽 03시에 초기화
   *
   * @throws Exception
   */
  @Scheduled(cron="00 03 00 * * *")
  public void setLotteCinemaLocation() throws Exception {
    // default Set
    MapUtil m = new MapUtil();
    ObjectMapper mapper = new ObjectMapper();

    // file Get
    InputStream is = this.getClass().getResourceAsStream("/lotteCinemaLocation.json");

    List<CinemaLocation> list = mapper.readValue(is, new TypeReference<List<CinemaLocation>>(){});

    // 로직 실행
    if(list.size() > 0) {
      for(CinemaLocation v : list) {
        if(m.searchLocalePointWithGoogle(v.getAddress()).getResults().size() > 0) {
          MapGoogleResultGeometryVO geo = m.searchLocalePointWithGoogle(v.getAddress()).getResults().get(0).getGeometry();
          final GeoJsonPoint locationPoint = new GeoJsonPoint(geo.getLocationLng(), geo.getLocationLat());
          v.setLocation(locationPoint);
        }
      }
      // db 초기화
      lotteCineLocalRepository.deleteAll();
      // db 저장
      lotteCineLocalRepository.insert(list);
    }
  }
}
