package com.iuom.springboot.test.process.common;

import com.iuom.springboot.common.crawler.CrawlerHelper;
import com.iuom.springboot.common.crawler.domain.Cinema;
import com.iuom.springboot.common.crawler.node.LotteCinemaNode;
import com.iuom.springboot.process.sample.dao.LotteCineDBRepository;
import com.iuom.springboot.test.process.config.LocalBaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class Crawling extends LocalBaseConfig {

    @Autowired
    private LotteCineDBRepository mongoDBCrawler;

    @Autowired
    private LotteCineDBRepository lotteCineDBRepository;

    @Override
    public void setUp() {

    }

    /**
     *
     * 예매 사이트 데이터 가져오기
     * @throws Exception
     */
    @Test
    public void doCollect() throws Exception {
        CrawlerHelper<Cinema> crawler = new CrawlerHelper<>();
        crawler.addExecuteNode(new LotteCinemaNode());
        crawler.addObserver(o -> {
            //DB delete
            mongoDBCrawler.deleteAll();
            //DB Insert
            o.parallelStream().forEach(v -> {
                mongoDBCrawler.insert(v);
            });
        });
        crawler.execute();
        log.info("lotte : {}", crawler.getData());
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
