package com.grepiu.www.process.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 * 공통 파일 VO
 *
 */
@Document(collection = "files")
@Data
public class Files {

  @Id
  private String id;
  private String fileName;
  private String originalFileName;
  private String rootPath;
  private String path;
  private Long size;

  @CreatedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date regDate;

  public String getFullFilePath() {
    return this.rootPath+path+fileName;
  }
}
