package com.iuom.springboot.common.util.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import lombok.Data;

/**
 *
 * 구글 결과 List
 *
 */
@Data
public class MapGoogleResultVO implements Serializable {
  private List<HashMap> addressComponents;
  private String formattedAddress;
  private MapGoogleResultGeometryVO geometry;
  private String placeId;
  private List<Object> types;
}
