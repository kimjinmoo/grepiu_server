package com.iuom.springboot.test;

import com.iuom.springboot.common.crawler.CrawlerHelper;
import com.iuom.springboot.common.crawler.Observer;
import com.iuom.springboot.common.crawler.domain.LotteCinema;
import com.iuom.springboot.common.crawler.node.LotteCinemaNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Slf4j
public class CrawlingTest {


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
            public void update(Object obj) {
                HashMap<String, Object> o = (HashMap<String, Object>) obj;
                o.forEach((key, value)->{

                });
                log.debug("obj event처리 ===>{}", (LotteCinema) obj);
            }
        });
        crawler.execute();
        log.debug("lotte : {}", crawler.getData());
    }
}
