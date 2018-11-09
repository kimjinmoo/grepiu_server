package com.grepiu.www.process.grepiu.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cloudStore")
@Data
public class CloudStore implements Serializable {

  @Id
  @ApiParam(value = "경로  /{id}/path...", hidden = true)
  String id;
  @ApiParam("경로  /{id}/path...")
  String path;
  @ApiParam("이름")
  String name;
  @ApiParam(value = "파일인 경우 파일 경로", hidden = true)
  String filePath;
  @ApiParam(value = "파일 저장시 이름", hidden = true)
  String fileSaveName;
  @ApiParam("속성 - P:폴더, F:파일")
  String attribute;
  @ApiParam("생성ID")
  String createId;
  @ApiParam(value = "생성일", hidden = true)
  Date createDate;
}
