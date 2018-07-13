package com.grepiu.www.process.sample.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepiu.www.process.common.tools.crawler.CrawlerHelper;
import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import com.grepiu.www.process.common.tools.crawler.domain.CinemaLocation;
import com.grepiu.www.process.common.tools.crawler.node.LotteCinemaNode;
import com.grepiu.www.process.common.tools.domain.MapGoogleResultGeometryVO;
import com.grepiu.www.process.common.utils.MapUtil;
import com.grepiu.www.process.sample.dao.LotteCineDBRepository;
import com.grepiu.www.process.sample.dao.LotteCineLocalRepository;
import com.grepiu.www.process.sample.dao.SampleDAO;
import com.grepiu.www.process.sample.dao.SampleMapper;
import com.grepiu.www.process.sample.dao.TestMongoDBRepository;
import com.grepiu.www.process.sample.domain.SampleMessage;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Api 서비스
 */
@Service
@Slf4j
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private SampleMapper sampleMapper;

    @Autowired
    private LotteCineDBRepository mongoDBCrawler;

    @Autowired
    private LotteCineLocalRepository lotteCineLocalRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public List<String> getSampleList() {
        List<String> samples = new ArrayList<>();
        samples.add("List");
        samples.add("Sample");
        samples.add("Test");
        samples.add("Hello");
        samples.add(sampleDAO.getSampleData());
        samples.add(sampleMapper.getSampleData());

        return samples;
    }

    @Override
    @Async
    public void collectionCinemaLocation() {
        MapUtil m = new MapUtil();
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = this.getClass().getResourceAsStream("/lotteCinemaLocation.json");
        try {
            lotteCineLocalRepository.deleteAll();
            List<CinemaLocation> list = mapper.readValue(is, new TypeReference<List<CinemaLocation>>(){});
            for(CinemaLocation v : list) {
                if(m.searchLocalePointWithGoogle(v.getAddress()).getResults().size() > 0) {
                    MapGoogleResultGeometryVO geo = m.searchLocalePointWithGoogle(v.getAddress()).getResults().get(0).getGeometry();
                    final GeoJsonPoint locationPoint = new GeoJsonPoint(geo.getLocationLng(), geo.getLocationLat());
                    v.setLocation(locationPoint);
                }
            }
            // db 저장
            lotteCineLocalRepository.insert(list);

            //완료 후 최종 이벤트 처리
            template.convertAndSend("/topic/messages",
                new SampleMessage("시스템 알림", "영화관 위치 수집 완료 신규 데이터를 확인하세요."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void collectionCinemaMovieInfo() {
        try {
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
                    new SampleMessage("시스템 알림", "영화 상영 정보 처리 완료 신규 데이터를 확인하세요."));
            });
            ch.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
