package com.iuom.springboot.test.process.common;

import com.iuom.springboot.common.crawler.CrawlerHelper;
import com.iuom.springboot.common.crawler.Observer;
import com.iuom.springboot.common.crawler.domain.Cinema;
import com.iuom.springboot.common.crawler.node.LotteCinemaNode;
import com.iuom.springboot.test.process.config.IntegrationTestBase;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class CrawlingTDD extends IntegrationTestBase {


    /**
     *
     * 예매 사이트 데이터 가져오기
     * @throws Exception
     */
    @Test
    public void reg() throws Exception {
        CrawlerHelper crawler = new CrawlerHelper();
        crawler.addNode(new LotteCinemaNode());
        crawler.addObserver(new Observer() {
            @Override
            public void update(List<Cinema> obj) {
                obj.forEach((v)->{
                    log.debug("obj event처리 ===>{}", v.getMovieInfo());
                });

            }
        });
        crawler.execute();
        log.debug("lotte : {}", crawler.getData());
    }
}
