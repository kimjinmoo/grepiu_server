package com.grepiu.www.process.grepiu.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 *
 * Post
 *
 */
@JsonDeserialize(contentUsing = StringDeserializer.class, contentAs = String.class)
@Data
@Document(collection = "post")
public class Post implements Serializable {

  @Parameter(hidden = true, description = "일련번호")
  @Id
  private long id;

  @Parameter(required = true, description = "범주")
  @NotNull
  private List<String> hashTag;

  @Parameter(required = true, description = "제목")
  @NotNull @NotEmpty
  private String subject;
  @Parameter(required = true, description = "내용")
  @NotNull @NotEmpty
  private String content;

  @Parameter(description = "등록자")
  private String regId;

  @CreatedDate
  @DateTimeFormat(iso = ISO.DATE_TIME)
  @Parameter(hidden = true, description = "등록일")
  private Date regDate;

  @Parameter(description = "수정자")
  private String modifyId;

  @LastModifiedDate
  @DateTimeFormat(iso = ISO.DATE_TIME)
  @Parameter(hidden = true, description = "최종수정일")
  private Date modifyDate;
}
