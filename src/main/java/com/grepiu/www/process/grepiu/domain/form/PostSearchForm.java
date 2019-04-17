package com.grepiu.www.process.grepiu.domain.form;

import com.grepiu.www.process.grepiu.common.SearchType;
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
  @ApiModelProperty("필터로 검색한다.")
  private String filter;
  @ApiModelProperty("HashTags, 해쉬테크로 검색한다.")
  private List<String> hashTags;
  @ApiModelProperty(value = "서치 타입", hidden = true)
  private SearchType searchType = SearchType.ALL_SEARCH;
}
