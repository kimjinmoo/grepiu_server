package com.grepiu.www.process.grepiu.entity;

import com.grepiu.www.process.common.api.entity.Files;
import com.grepiu.www.process.grepiu.common.CloudAttributeType;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cloudStore")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudStore implements Serializable {

  @Id
  @Parameter(description = "자동생성", hidden = true)
  String id;

  @Parameter(description = "상위ID, 최상위는 기본 uid로 지정", hidden = true)
  String parentId;

  @Parameter(description = "이름")
  @NotEmpty
  String name;

  @Parameter(description = "파일 ID", hidden = true)
  @DBRef
  Files files;

  @Parameter(description = "속성 - D:폴더, F:파일, Default D", hidden = true)
  @NotEmpty
  @Builder.Default
  String attribute = CloudAttributeType.DIRECTORY.getCode();

  @Parameter(description = "접근가능 유저", hidden = true)
  @Indexed
  List<String> authorizedUsers;

  @Parameter(description = "생성ID", hidden = true)
  String createId;

  @Parameter(description = "생성일", hidden = true)
  @CreatedDate
  Date createDate;

  @Parameter(description = "수정자", hidden = true)
  String updateId;

  @Parameter(description = "수정일", hidden = true)
  @LastModifiedDate
  Date updateDate;
}
