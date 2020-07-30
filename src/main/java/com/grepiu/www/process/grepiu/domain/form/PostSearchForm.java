package com.grepiu.www.process.grepiu.domain.form;

import com.grepiu.www.process.grepiu.common.SearchType;
import io.swagger.v3.oas.annotations.Parameter;
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
  @Parameter(description = "필터로 검색한다.")
  private String filter;
  @Parameter(description = "HashTags, 해쉬테크로 검색한다.")
  private List<String> hashTags;
  @Parameter(description = "서치 타입", hidden = true)
  private SearchType searchType = SearchType.ALL_SEARCH;
}
