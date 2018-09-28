package com.grepiu.www.process.grepiu.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HashTagStatistics {
  private String name;
  private Long count;
}
