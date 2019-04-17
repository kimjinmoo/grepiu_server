package com.grepiu.www.process.grepiu.common;

import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Lists;

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
