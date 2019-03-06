package com.grepiu.www.process.grepiu.entity;

import com.grepiu.www.process.common.api.entity.Files;
import com.grepiu.www.process.common.constant.CloudAttributeType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cloudStore")
@Data
public class CloudStore implements Serializable {

  @Id
  @ApiModelProperty(value = "자동생성", hidden = true)
  String id;
  @ApiModelProperty(value = "상위폴더", hidden = true)
  String pid;
  @ApiModelProperty("이름")
  @NotEmpty
  String name;
  @ApiModelProperty(value = "파일 ID", hidden = true)
  @DBRef
  Files files;
  @ApiModelProperty(value = "속성 - D:폴더, F:파일", hidden = true)
  @NotEmpty
  String attribute = CloudAttributeType.DIRECTORY.getCode();
  @ApiModelProperty(value = "접근가능 유저", hidden = true)
  @Indexed
  List<String> authorizedUsers;
  @ApiModelProperty(value = "생성ID", hidden = true)
  String createId;
  @ApiModelProperty(value = "생성일", hidden = true)
  @CreatedDate
  Date createDate;
}
