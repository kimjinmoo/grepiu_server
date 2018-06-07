package com.grepiu.www.process.common.tools.domain;

import lombok.Builder;
import lombok.Data;

/**
 *
 * 공통 파일 VO
 *
 */
@Data
public class FileVO {
  private String fileName;
  private String originalFileName;
  private String rootPath;
  private Long size;

  public String getFullFilePath() {
    return this.rootPath+fileName;
  }
}
