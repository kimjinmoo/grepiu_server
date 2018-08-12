package com.grepiu.www.process.api.domain;

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
  private String path;
  private Long size;

  public String getFullFilePath() {
    return this.rootPath+path+fileName;
  }
}
