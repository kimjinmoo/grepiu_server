package com.grepiu.www.process.grepiu.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepiu.www.process.common.api.domain.Message;
import com.grepiu.www.process.common.helper.GoogleMapParserHelper;
import com.grepiu.www.process.common.tools.crawler.domain.CineType;
import com.grepiu.www.process.common.tools.crawler.domain.MapGoogleResultGeometryVO;
import com.grepiu.www.process.common.tools.crawler.entity.Cinema;
import com.grepiu.www.process.common.tools.crawler.entity.CinemaLocation;
import com.grepiu.www.process.common.tools.crawler.module.CrawlerExecuteOptions;
import com.grepiu.www.process.common.tools.crawler.module.SeleniumConnect;
import com.grepiu.www.process.common.tools.crawler.node.CGVCinemaNode;
import com.grepiu.www.process.common.tools.crawler.node.LotteCinemaNode;
import com.grepiu.www.process.common.utils.UUIDUtils;
import com.grepiu.www.process.grepiu.dao.CineDBRepository;
import com.grepiu.www.process.grepiu.dao.CineLocalRepository;
import com.grepiu.www.process.grepiu.dao.RealtimeVoteRepository;
import com.grepiu.www.process.grepiu.dao.SlinkRepository;
import com.grepiu.www.process.grepiu.domain.CineLocalFilter;
import com.grepiu.www.process.grepiu.domain.form.CinemaInfoOptionForm;
import com.grepiu.www.process.grepiu.entity.RealtimeVote;
import com.grepiu.www.process.grepiu.entity.Slink;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * Lab 관련 서비스 이다.
 *
 */
@Service
@Slf4j
public class LabServiceImpl implements LabService {

  private final CineDBRepository mongoDBCrawler;

  private final CineLocalRepository cineLocalRepository;

  private final SimpMessagingTemplate template;

  private final GoogleMapParserHelper googleMapParserHelper;

  private final SlinkRepository slinkRepository;

  private final RealtimeVoteRepository realtimeVoteRepository;

  public LabServiceImpl(CineDBRepository mongoDBCrawler,
      CineLocalRepository cineLocalRepository, SimpMessagingTemplate template,
      GoogleMapParserHelper googleMapParserHelper, SlinkRepository slinkRepository,
      RealtimeVoteRepository realtimeVoteRepository
  ) {
    this.mongoDBCrawler = mongoDBCrawler;
    this.cineLocalRepository = cineLocalRepository;
    this.template = template;
    this.googleMapParserHelper = googleMapParserHelper;
    this.slinkRepository = slinkRepository;
    this.realtimeVoteRepository = realtimeVoteRepository;
  }

  /**
   *
   * 영화관 위치 정보 수동 등록
   *
   */
  @Async
  @Override
  public void collectCinemaLocation() {
    log.info(":::::::::::::Start Collect Movie Location ::::::::::::::::");
    ObjectMapper mapper = new ObjectMapper();
    InputStream is = this.getClass().getResourceAsStream("/config/lotteCinemaLocation.json");
    try {
      cineLocalRepository.deleteAll();
      List<CinemaLocation> list = mapper.readValue(is, new TypeReference<List<CinemaLocation>>() {
      });
      for (CinemaLocation v : list) {
        if (googleMapParserHelper.convertToLanLongFromAddress(v.getAddress()).getResults().size()
            > 0) {
          MapGoogleResultGeometryVO geo = googleMapParserHelper
              .convertToLanLongFromAddress(v.getAddress()).getResults().get(0).getGeometry();
          final GeoJsonPoint locationPoint = new GeoJsonPoint(geo.getLocationLng(),
              geo.getLocationLat());
          v.setLocation(locationPoint);
        }
      }
      // db 저장
      cineLocalRepository.insert(list);

      //완료 후 최종 이벤트 처리
      template.convertAndSend("/topic/messages",
          new Message("시스템 알림", "영화관 위치 수집 완료 신규 데이터를 확인하세요."));
    } catch (Exception e) {
      e.printStackTrace();
    }
    log.info(":::::::::::::Complete Collect Movie Location ::::::::::::::::");
  }

  /**
   *
   * 롯데 시네마 상영 정보 수동 크롤링
   *
   * @param cinemaInfoOptionForm CinemaInfoOptionForm 객체
   */
  @Async
  @Override
  public void collectLotteCinemaMovieInfo(CinemaInfoOptionForm cinemaInfoOptionForm) {
    try {
      SeleniumConnect<List<Cinema>> connect = new SeleniumConnect<>();
      if (cinemaInfoOptionForm.isEnableProxy()) {
        connect.init(CrawlerExecuteOptions.builder().isProxyUse(true)
                .isProxyUse(cinemaInfoOptionForm.isEnableProxy())
                .proxyServerIp(cinemaInfoOptionForm.getProxyServerIp()).build(),
            new LotteCinemaNode());
      } else {
        connect.init(new LotteCinemaNode());
      }

      List<Cinema> infos = connect.execute();
      if (infos.size() > 0) {
        mongoDBCrawler.deleteByType("lotte");
        infos.stream().forEach(v -> {
          mongoDBCrawler.insert(v);

        });
      }
      template
          .convertAndSend("/topic/messages", new Message("시스템 알림", "수동 크롤링 처리 완료 신규 데이터를 확인하세요."));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * CGV 상영 정보 수동 크롤링
   *
   */
  @Async
  @Override
  public void collectCgvCinemaMovieInfo() {
    try {
      SeleniumConnect<List<Cinema>> connect = new SeleniumConnect<>();
      connect.init(new CGVCinemaNode());

      log.info("start cgv m---------------");
      List<Cinema> cgvInfos = connect.execute();
      if (cgvInfos.size() > 0) {
        mongoDBCrawler.deleteByType("cgv");
        cgvInfos.stream().forEach(v -> {
          log.info("cgv : {}", v);
          mongoDBCrawler.insert(v);

        });
      }
      log.info("end cgv m---------------");
      template.convertAndSend("/topic/messages",
          new Message("시스템 알림", "CGV 수동 크롤링 처리 완료 신규 데이터를 확인하세요."));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * 영화관 위치 정보를 조회 한다.
   *
   * @param cineLocalFilter CineLocalFilter 객체
   * @return List<CinemaLocation> 객체
   */
  @Override
  public List<CinemaLocation> findCineLocale(CineLocalFilter cineLocalFilter) {
    List<CinemaLocation> cineLists = Lists.newArrayList();
    // Set CineType
    CineType cineType = Optional.ofNullable(cineLocalFilter.getType()).orElse(CineType.ALL);
    // 검색
    switch (cineType) {
      case LOTTE:
      case CGV:
        cineLists = cineLocalRepository.findByType(cineLocalFilter.getType().getCode());
        break;
      case ALL:
        cineLists = cineLocalRepository.findAllBy();
        break;
    }
    return cineLists;
  }

  @Override
  public String saveSlink(String fUrl) {
    Slink slink = new Slink();
    slink.setSUrl(UUIDUtils.getUUID());
    slink.setFUrl(fUrl);
    slinkRepository.save(slink);

    return slink.getSUrl();
  }

  @Override
  public Slink findSlink(String sUrl) {
    return slinkRepository.findBySUrl(sUrl).get();
  }

  @Override
  public Slink updateSlink(String sUrl, String fUrl) {
    Slink slink = slinkRepository.findBySUrl(fUrl).get();
    slink.setFUrl(fUrl);
    return slinkRepository.save(slink);
  }

  @Override
  public void deleteSlink(String sUrl) {
    slinkRepository.deleteBySUrl(sUrl);
  }

  @Override
  public RealtimeVote saveRealtimeVote(RealtimeVote realtimeVote) {
    return realtimeVoteRepository.save(realtimeVote);
  }

  @Override
  public List<RealtimeVote> getRealtimeVotes() {
    List<RealtimeVote> votes = realtimeVoteRepository.findAll();
    votes.stream().map(o-> {
      o.getItems().stream().map(item->{
        item.setVote(item.getVoteIp().size());
        item.setVoteIp(null);
        return item;
      }).collect(Collectors.toList());
      return o;
    }).collect(Collectors.toList());
    return votes;
  }

  @Override
  public RealtimeVote getRealtimeVoteById(String id) throws Exception {
    RealtimeVote realtimeVote = realtimeVoteRepository.findById(id).orElseThrow(Exception::new);
    realtimeVote.getItems().stream().map(o-> {
      o.setVote(o.getVoteIp().size());
      o.setVoteIp(null);
      return o;
    }).collect(Collectors.toList());
    return realtimeVote;
  }

  @Override
  public void deleteRealtimeVote(String id) {
    realtimeVoteRepository.deleteById(id);
  }

  @Override
  public void addRealtimeVote(String id, String ip, int voteIndex) throws Exception {
    RealtimeVote realtimeVote = realtimeVoteRepository.findById(id).orElseThrow(Exception::new);

    if (realtimeVote.getItems()
        .stream()
        .filter(o -> o.getVoteIp().equals(ip)).findAny()
        .isPresent()) {
        throw new Exception("중복 투표는 할 수 없습니다.");
    }
    // vote
    realtimeVote.getItems().get(voteIndex).getVoteIp().add(ip);

    // save
    realtimeVoteRepository.save(realtimeVote);
  }
}
