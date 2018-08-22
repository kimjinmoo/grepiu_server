package com.grepiu.test.process.common;

import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.common.tools.crawler.domain.MapGoogleResultGeometryVO;
import com.grepiu.www.process.common.utils.MapUtils;
import com.grepiu.www.process.grepiu.service.LabService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MapsTDD extends LocalBaseConfig {

  @Autowired
  LabService labService;

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
    labService.collectionCinemaLocation();
  }
}
