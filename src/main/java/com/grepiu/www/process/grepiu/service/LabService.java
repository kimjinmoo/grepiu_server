package com.grepiu.www.process.grepiu.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import com.grepiu.www.process.common.tools.crawler.domain.CinemaLocation;
import com.grepiu.www.process.common.tools.crawler.module.CrawlerExecuteOptions;
import com.grepiu.www.process.common.tools.crawler.module.SeleniumConnect;
import com.grepiu.www.process.common.tools.crawler.node.CGVCinemaNode;
import com.grepiu.www.process.common.tools.crawler.node.LotteCinemaNode;
import com.grepiu.www.process.common.tools.crawler.domain.MapGoogleResultGeometryVO;
import com.grepiu.www.process.common.helper.GoogleMapParserHelper;
import com.grepiu.www.process.grepiu.dao.LotteCineDBRepository;
import com.grepiu.www.process.grepiu.dao.LotteCineLocalRepository;
import com.grepiu.www.process.common.api.domain.Message;
import com.grepiu.www.process.grepiu.domain.form.CinemaInfoOptionForm;
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
    public void collectionCinemaMovieInfo(CinemaInfoOptionForm cinemaInfoOptionForm) {
        try {
            SeleniumConnect<List<Cinema>> connect = new SeleniumConnect<>();
            if(cinemaInfoOptionForm.isEnableProxy()) {
                connect.init(CrawlerExecuteOptions.builder().isProxyUse(true)
                        .isProxyUse(cinemaInfoOptionForm.isEnableProxy())
                        .proxyServerIp(cinemaInfoOptionForm.getProxyServerIp()).build(),
                    new LotteCinemaNode());
            } else {
                connect.init(new LotteCinemaNode());
            }

            List<Cinema> infos = connect.execute();
            if (infos.size() > 0) {
                mongoDBCrawler.deleteAll();
                infos.stream().forEach(v -> {
                    mongoDBCrawler.insert(v);

                });
            }
            template.convertAndSend("/topic/messages", new Message("시스템 알림", "수동 크롤링 처리 완료 신규 데이터를 확인하세요."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void collectionCgvCinemaMovieInfo(CinemaInfoOptionForm cinemaInfoOptionForm) {
        try {
            SeleniumConnect<List<Cinema>> connect = new SeleniumConnect<>();
            connect.init(new CGVCinemaNode());

            List<Cinema> cgvInfos = connect.execute();
            if(cgvInfos.size() > 0) {
//                mongoDBCrawler.deleteAll();
                connect.execute().stream().forEach(v -> {
                    mongoDBCrawler.insert(v);

                });
            }
            template.convertAndSend("/topic/messages", new Message("시스템 알림", "CGV 수동 크롤링 처리 완료 신규 데이터를 확인하세요."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
