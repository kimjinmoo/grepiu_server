package com.grepiu.www.process.common.crawler.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * 시네마 위치 정보
 *
 * Mongodb에서 index 세팅
 * <pre>
 * ref : https://docs.mongodb.com/manual/core/2dsphere/
 * use study;
 * db.cinemaLocation.createIndex({location : "2dsphere"});
 * </pre>
 */
@Data
@Document(collection="cinemaLocation")
public class CinemaLocation {

  @Id
  private String id;          // ID
  @Indexed
  private String storeName;  // 매장명
  private String address;     // 주소
  private GeoJsonPoint location; // 좌표
  private Double distance;       // 거리

  public CinemaLocation(GeoJsonPoint location) {
    this.location = location;
  }

  public CinemaLocation() {}
}
