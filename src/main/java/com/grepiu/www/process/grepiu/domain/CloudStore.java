package com.grepiu.www.process.grepiu.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cloudStore")
@Data
public class CloudStore implements Serializable {

  @Id
  @ApiModelProperty(value = "자동생성", hidden = true)
  String id;
  @ApiModelProperty("경로  /{id}/path...")
  @Indexed
  @NotEmpty
  String path;
  @ApiModelProperty("이름")
  @NotEmpty
  String name;
  @ApiModelProperty(value = "파일인 경우 파일 경로", hidden = true)
  String filePath;
  @ApiModelProperty(value = "파일 저장시 이름")
  String fileSaveName;
  @ApiModelProperty("속성 - P:폴더, F:파일")
  @NotEmpty
  String attribute;
  @ApiModelProperty(value = "접근가능 유저", hidden = true)
  List<String> authorizedUsers;
  @ApiModelProperty(value = "생성ID", hidden = true)
  String createId;
  @ApiModelProperty(value = "생성일", hidden = true)
  Date createDate;
}
