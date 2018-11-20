package com.grepiu.www.process.sample.util.socket.module;


import com.grepiu.www.process.sample.util.socket.module.model.*;

/**
 *
 * 세종문화회관 팩토리
 *
 */
public class SejongFactory {

  public enum TYPE {
    WATCH_GRADE("9101"), GENRE_SEARCH("9102");

    String code;
    TYPE(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  // 팩토리
  public static SejongSocket create(TYPE type) {
    switch (type) {
      case WATCH_GRADE:
        return new WatchGrade(type.getCode());
      case GENRE_SEARCH:
        return new GenreSearch(type.getCode());
    }
    return null;
  }
}
