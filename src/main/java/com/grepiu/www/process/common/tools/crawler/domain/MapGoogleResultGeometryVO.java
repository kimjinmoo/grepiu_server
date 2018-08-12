package com.grepiu.www.process.common.tools.crawler.domain;

import java.util.HashMap;
import java.util.Optional;
import lombok.Data;

/**
 *
 * 구글 Maps Geometry VO
 *
 */
@Data
public class MapGoogleResultGeometryVO {
  private HashMap location;
  private String locationType;
  private HashMap viewport;

  public Double getLocationLat() {
    return (Double) Optional.ofNullable(location.get("lat")).orElse(0d);
  }

  public Double getLocationLng() {
    return (Double) Optional.ofNullable(location.get("lng")).orElse(0d);
  }
}
