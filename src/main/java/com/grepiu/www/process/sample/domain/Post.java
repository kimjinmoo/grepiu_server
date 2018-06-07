package com.grepiu.www.process.sample.domain;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
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

  @ApiModelProperty(hidden = true, name = "아이디") @Id private String id;
  @ApiModelProperty(required = true,  name = "제목") private String subject;
  @ApiModelProperty(required = true, name = "내용") private String content;

  @ApiModelProperty(name="등록자") private String regId;

  @CreatedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @ApiModelProperty(hidden = true, name="등록일") private Date regDate;

  @ApiModelProperty(name="수정자") private String modifyId;

  @LastModifiedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @ApiModelProperty(hidden = true, name="최종수정일") private Date modifyDate;
}
