package com.grepiu.test.process.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.common.crawler.domain.CinemaLocation;
import com.grepiu.www.process.common.domain.MapGoogleResultGeometryVO;
import com.grepiu.www.process.common.utils.MapUtil;
import com.grepiu.www.process.sample.dao.LotteCineLocalRepository;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Slf4j
public class MapsTDD extends LocalBaseConfig {

  @Autowired
  private LotteCineLocalRepository lotteCineLocalRepository;

  @Override
  public void setUp() {

  }

  /**
   *
   * 맵에서 좌표 엊기 with Google
   *
   */
  @Test
  public void searchMap() {
    MapUtil m = new MapUtil();
    MapGoogleResultGeometryVO geo = m.searchLocalePointWithGoogle("서울특별시 송파구 올림픽로 300 롯데월드몰 엔터테인먼트동").getResults().get(0).getGeometry();
    log.info("lat : {}", geo.getLocationLat());
    log.info("lng : {}", geo.getLocationLng());
  }

  @Test
  public void getLocale() throws Exception {
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
       log.info("info : {} ",list);
       // db 저장
       lotteCineLocalRepository.insert(list);
    } catch (Exception e) {
      e.printStackTrace();
    }
//    File file = new ClassPathResource("").getFile();
//    lotteCineLocalRepository.save()
//    log.info("file : {}", file.isFile());
  }
}
