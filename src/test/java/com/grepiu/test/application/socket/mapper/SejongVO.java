package com.grepiu.test.application.socket.mapper;

import com.grepiu.www.process.sample.util.socket.module.SejongFactory;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Lists;
import org.json.JSONArray;

/**
 *
 * 공통 VO
 *
 */
public class SejongVO {

  private Type mType;

  public SejongVO(SejongFactory.TYPE type, String res) throws Exception {
    // validation
    if (res.length() > 0) {
      mType = new Type();
      switch (type) {
        case WATCH_GRADE:
          mType.setType(new TypeAMap(res));
          break;
        case GENRE_SEARCH:
          mType.setType(new TypeBMap(res));
          break;
      }
    }
  }

  public String getHeader() {
    return this.mType.getType().getHeader();
  }

  public List<OrderMapper> getOrderMapper() {
    return (List<OrderMapper>) this.mType.getType().getData();
  }

  public List<SearchMapper> getSearchMapper() {
    return (List<SearchMapper>) this.mType.getType().getData();
  }

}
