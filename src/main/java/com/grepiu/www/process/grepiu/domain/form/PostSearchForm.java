package com.grepiu.www.process.grepiu.domain.form;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.assertj.core.util.Lists;
import org.springframework.util.StringUtils;

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

  public enum SearchType {
    ONLY_FILTER("OF"), ONLY_HASH_TAG("OHT"), ALL_SEARCH("AS"), ALL_LIST("AL");

    String searchType;

    SearchType(String searchType) {
      this.searchType = searchType;
    }

    public SearchType getSearchType(String filter, List hashTags) {
      //default AL
      String searchTypeSelected = "ALL_LIST";
      // Filter Only
      if(Optional.ofNullable(filter).orElse("").trim().length() > 0 &&
          Optional.ofNullable(hashTags).orElse(Lists.newArrayList()).size() == 0) {
        searchTypeSelected = "ONLY_FILTER";
      }
      // Hashtag Only
      if(!(Optional.ofNullable(filter).orElse("").trim().length() > 0) &&
          Optional.ofNullable(hashTags).orElse(Lists.newArrayList()).size() > 0) {
        searchTypeSelected = "ONLY_HASH_TAG";
      }
      // All Filter
      if(Optional.ofNullable(filter).orElse("").trim().length() > 0  &&
          Optional.ofNullable(hashTags).orElse(Lists.newArrayList()).size() > 0 ) {
        searchTypeSelected = "ALL_SEARCH";
      }
      return SearchType.valueOf(searchTypeSelected);
    }
  }
}
