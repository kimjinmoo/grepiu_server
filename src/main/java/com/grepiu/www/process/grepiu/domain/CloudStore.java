package com.grepiu.www.process.grepiu.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cloudStore")
@Data
public class CloudStore {

  @Id
  String id;
  @ApiParam("경로")
  String path;
  @ApiParam("파일명")
  String realFileName;
  @ApiParam("원래 파일명")
  String originalFile;
  @ApiParam("속성")
  String attribute;
  @ApiParam("생성ID")
  String createId;
  @ApiParam("생성일")
  Date createDate;
}
