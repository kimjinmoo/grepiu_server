package com.grepiu.www.process.grepiu.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepiu.www.process.common.tools.crawler.CrawlerHelper;
import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import com.grepiu.www.process.common.tools.crawler.domain.CinemaLocation;
import com.grepiu.www.process.common.tools.crawler.node.LotteCinemaNode;
import com.grepiu.www.process.common.tools.crawler.domain.MapGoogleResultGeometryVO;
import com.grepiu.www.process.common.helper.GoogleMapParserHelper;
import com.grepiu.www.process.grepiu.dao.LotteCineDBRepository;
import com.grepiu.www.process.grepiu.dao.LotteCineLocalRepository;
import com.grepiu.www.process.common.api.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class LabService {


    @Autowired
    private LotteCineDBRepository mongoDBCrawler;

    @Autowired
    private LotteCineLocalRepository lotteCineLocalRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private GoogleMapParserHelper googleMapParserHelper;

    @Async
    public void collectionCinemaLocation() {
        log.info(":::::::::::::Start Collect Movie Location ::::::::::::::::");
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = this.getClass().getResourceAsStream("/config/lotteCinemaLocation.json");
        try {
            lotteCineLocalRepository.deleteAll();
            List<CinemaLocation> list = mapper.readValue(is, new TypeReference<List<CinemaLocation>>(){});
            for(CinemaLocation v : list) {
                if(googleMapParserHelper.convertToLanLongFromAddress(v.getAddress()).getResults().size() > 0) {
                    MapGoogleResultGeometryVO geo = googleMapParserHelper.convertToLanLongFromAddress(v.getAddress()).getResults().get(0).getGeometry();
                    final GeoJsonPoint locationPoint = new GeoJsonPoint(geo.getLocationLng(), geo.getLocationLat());
                    v.setLocation(locationPoint);
                }
            }
            // db 저장
            lotteCineLocalRepository.insert(list);

            //완료 후 최종 이벤트 처리
            template.convertAndSend("/topic/messages",
                    new Message("시스템 알림", "영화관 위치 수집 완료 신규 데이터를 확인하세요."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(":::::::::::::Complete Collect Movie Location ::::::::::::::::");
    }

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
                        new Message("시스템 알림", "영화 상영 정보 처리 완료 신규 데이터를 확인하세요."));
            });
            ch.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
