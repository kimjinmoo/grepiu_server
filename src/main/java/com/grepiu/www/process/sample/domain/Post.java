package com.grepiu.www.process.sample.domain;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * Post
 *
 */
@Data
@Document(collection = "post")
public class Post {

  @ApiModelProperty(hidden = true, value = "일련번호") @Id private String id;
  @ApiModelProperty(hidden = true, value = "범주") @Id private String category;
  @ApiModelProperty(required = true,  value = "제목") @NonNull private String subject;
  @ApiModelProperty(required = true, value = "내용") @NonNull private String content;

  @ApiModelProperty(value="등록자") private String regId;

  @CreatedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @ApiModelProperty(hidden = true, value="등록일") private Date regDate;

  @ApiModelProperty(value="수정자") private String modifyId;

  @LastModifiedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @ApiModelProperty(hidden = true, value="최종수정일") private Date modifyDate;
}
