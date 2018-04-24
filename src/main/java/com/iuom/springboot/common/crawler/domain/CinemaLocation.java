package com.iuom.springboot.common.crawler.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * 시네마 위치 정보
 *
 */
@Data
@Document(collection="cinemaLocation")
public class CinemaLocation {

  @Id
  private String id;          // ID
  @Indexed
  private String storeName;  // 매장명
  private String address;     // 주소
  private String lat;         // 위도
  private String lon;         // 경도
}
