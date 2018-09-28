package com.grepiu.www.process.grepiu.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 *
 * Post 검색 Filter
 *
 */
@Data
public class PostSearchForm implements Serializable {
  @ApiModelProperty("HashTags, 해쉬테크로 검색한다.")
  private List<String> hashTags;
}
