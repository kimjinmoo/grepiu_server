package com.grepiu.www.process.grepiu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
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

  @ApiModelProperty(hidden = true, value = "일련번호")
  @Id
  private long id;

  @ApiModelProperty(required = true, value = "범주")
  @NotNull
  private List<String> hashTag;

  @ApiModelProperty(required = true, value = "제목")
  @NotNull @NotEmpty
  private String subject;
  @ApiModelProperty(required = true, value = "내용")
  @NotNull @NotEmpty
  private String content;

  @ApiModelProperty(value = "등록자")
  private String regId;

  @CreatedDate
  @DateTimeFormat(iso = ISO.DATE_TIME)
  @ApiModelProperty(hidden = true, value = "등록일")
  private Date regDate;

  @ApiModelProperty(value = "수정자")
  private String modifyId;

  @LastModifiedDate
  @DateTimeFormat(iso = ISO.DATE_TIME)
  @ApiModelProperty(hidden = true, value = "최종수정일")
  private Date modifyDate;
}
