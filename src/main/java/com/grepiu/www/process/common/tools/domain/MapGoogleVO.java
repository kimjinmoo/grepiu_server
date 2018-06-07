package com.grepiu.www.process.common.tools.domain;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 *
 * 구글 Maps
 *
 */
@Data
public class MapGoogleVO implements Serializable {
  private List<MapGoogleResultVO> results;
  private String status;
}
