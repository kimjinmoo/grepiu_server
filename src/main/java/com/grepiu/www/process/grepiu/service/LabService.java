package com.grepiu.www.process.grepiu.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepiu.www.process.common.tools.crawler.domain.CineType;
import com.grepiu.www.process.common.tools.crawler.entity.Cinema;
import com.grepiu.www.process.common.tools.crawler.entity.CinemaLocation;
import com.grepiu.www.process.common.tools.crawler.module.CrawlerExecuteOptions;
import com.grepiu.www.process.common.tools.crawler.module.SeleniumConnect;
import com.grepiu.www.process.common.tools.crawler.node.CGVCinemaNode;
import com.grepiu.www.process.common.tools.crawler.node.LotteCinemaNode;
import com.grepiu.www.process.common.tools.crawler.domain.MapGoogleResultGeometryVO;
import com.grepiu.www.process.common.helper.GoogleMapParserHelper;
import com.grepiu.www.process.grepiu.dao.CineDBRepository;
import com.grepiu.www.process.grepiu.dao.CineLocalRepository;
import com.grepiu.www.process.common.api.domain.Message;
import com.grepiu.www.process.grepiu.domain.CineLocalFilter;
import com.grepiu.www.process.grepiu.domain.form.CinemaInfoOptionForm;
import com.grepiu.www.process.grepiu.entity.Slink;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public interface LabService {

  // 영화관 장소를 수집힌다.
  public void collectCinemaLocation();

  // 롯데 영화관 영화 정보를 수집힌다.
  public void collectLotteCinemaMovieInfo(CinemaInfoOptionForm cinemaInfoOptionForm);

  // Cgv 영화관 영화 정보를 수집힌다.
  public void collectCgvCinemaMovieInfo();

  // 영화관 정보를 가져온다.
  public List<CinemaLocation> findCineLocale(CineLocalFilter cineLocalFilter);

  // Short Url 정보를 저장한다.
  public String saveSlink(String fUrl);

  // Short Url 정보를 가져온다.
  public Slink findSlink(String sUrl);

  // Short Url 정보를 수정한다.
  public Slink updateSlink(String sUrl, String fUrl);

  // SHort Url 정보를 삭제한다.
  public void deleteSlink(String sUrl);
}
