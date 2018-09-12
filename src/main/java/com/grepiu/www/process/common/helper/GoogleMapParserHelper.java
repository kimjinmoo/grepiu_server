package com.grepiu.www.process.common.helper;

import com.grepiu.www.process.common.tools.crawler.domain.MapGoogleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * 지도 유틸
 *
 */
@Slf4j
@Component
public class GoogleMapParserHelper {

  @Value("${key.google.map}")
  private String GOOGLE_API_KEY;

  @Value("${key.google.geo_url}")
  private String GOOGLE_GEO_CODING_API_URL;

  /**
   *
   * 주소를 받아서 좌표 값으로 변환한다.
   *
   * @param address
   * @return
   */
  public MapGoogleVO convertToLanLongFromAddress(String address) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GOOGLE_GEO_CODING_API_URL)
        .queryParam("address", address)
        .queryParam("key", GOOGLE_API_KEY);
    HttpEntity<?> entity = new HttpEntity<>(headers);

    HttpEntity<MapGoogleVO> response = restTemplate.exchange(
        builder.build(false).toString(),
        HttpMethod.GET,
        entity,
        MapGoogleVO.class);
    return response.getBody();
  }

}
