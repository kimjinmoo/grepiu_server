package com.iuom.springboot.test.process.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iuom.springboot.common.crawler.domain.CinemaGeo;
import com.iuom.springboot.common.crawler.domain.CinemaLocation;
import com.iuom.springboot.common.util.MapUtils;
import com.iuom.springboot.common.util.domain.MapGoogleResultGeometryVO;
import com.iuom.springboot.process.sample.dao.LotteCineLocalRepository;
import com.iuom.springboot.test.process.config.LocalBaseConfig;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    MapUtils m = new MapUtils();
    MapGoogleResultGeometryVO geo = m.searchLocalePointWithGoogle("서울특별시 송파구 올림픽로 300 롯데월드몰 엔터테인먼트동").getResults().get(0).getGeometry();
    log.info("lat : {}", geo.getLocationLat());
    log.info("lng : {}", geo.getLocationLng());
  }

  @Test
  public void getLocale() throws Exception {
    MapUtils m = new MapUtils();
    ObjectMapper mapper = new ObjectMapper();
    InputStream is = this.getClass().getResourceAsStream("/lotteCinemaLocation.json");
    try {
       List<CinemaLocation> list = mapper.readValue(is, new TypeReference<List<CinemaLocation>>(){});
       list.parallelStream().map(v->{
         MapGoogleResultGeometryVO geo = m.searchLocalePointWithGoogle(v.getAddress()).getResults().get(0).getGeometry();
         CinemaGeo g = new CinemaGeo();
         g.setLat(geo.getLocationLat());
         g.setLng(geo.getLocationLng());
         v.setPosition(g);
         return v;
       }).collect(Collectors.toList());
       // db 저장
       lotteCineLocalRepository.deleteAll();
       lotteCineLocalRepository.insert(list);
    } catch (Exception e) {
      e.printStackTrace();
    }
//    File file = new ClassPathResource("").getFile();
//    lotteCineLocalRepository.save()
//    log.info("file : {}", file.isFile());
  }
}
