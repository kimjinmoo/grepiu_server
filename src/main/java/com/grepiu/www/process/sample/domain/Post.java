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

  @ApiModelProperty(hidden = true) @Id private String id;
  @ApiModelProperty(required = true) private String subject;
  @ApiModelProperty(required = true)private String content;

  @ApiModelProperty(hidden = true) private String regId;

  @CreatedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @ApiModelProperty(hidden = true) private Date regDate;

  private String modifyId;

  @LastModifiedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @ApiModelProperty(hidden = true) private Date modifyDate;
}
