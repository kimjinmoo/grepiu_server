package com.grepiu.test.process.common;

import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import com.grepiu.www.process.common.tools.crawler.module.SeleniumConnect;
import com.grepiu.www.process.common.tools.crawler.node.CGVCinemaNode;
import com.grepiu.www.process.common.tools.crawler.node.LotteCinemaNode;
import com.grepiu.www.process.common.tools.crawler.node.SampleNode;
import com.grepiu.www.process.grepiu.dao.LotteCineDBRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CrawlingTDD extends LocalBaseConfig {

    @Autowired
    private LotteCineDBRepository mongoDBCrawler;

    @Autowired
    private LotteCineDBRepository lotteCineDBRepository;

    @Override
    public void setUp() {

    }

    /**
     *
     * 샘플 사이트 데이터 가져오기
     * @throws Exception
     */
    @Test
    public void doCollectSample() throws Exception {

        SeleniumConnect<String> connect = new SeleniumConnect<>();
        connect.init(new SampleNode());
        log.info("data : {}", connect.execute());
    }

  /**
   *
   * 샘플 사이트 데이터 가져오기
   * @throws Exception
   */
  @Test
  public void doCollectLotte() throws Exception {

    SeleniumConnect<List<Cinema>> connect = new SeleniumConnect<>();
    connect.init(new LotteCinemaNode());
    log.info("data : {}", connect.execute());
  }

  /**
   *
   * 샘플 사이트 데이터 가져오기
   * @throws Exception
   */
  @Test
  public void doCollectCgv() throws Exception {

    SeleniumConnect<List<Cinema>> connect = new SeleniumConnect<>();
    connect.init(new CGVCinemaNode());
    log.info("data : {}", connect.execute());
  }

    /**
     *
     * 롯데 시네마 위치 정보 수집
     *
     * @throws Exception
     */
    @Test
    public void lotteLocationSearch() throws Exception {
        log.info("{}", lotteCineDBRepository.findAllBy().stream()
            .filter(v -> v.getMovieInfo().containsKey("서울대입구")).findFirst().get().getMovieInfo()
            .get("서울대입구"));

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
