package com.grepiu.www.process.sample.util.socket.module.model;

import com.grepiu.www.process.sample.util.socket.module.domain.GenerSearchBody;
import com.grepiu.www.process.sample.util.socket.module.domain.GenerSearchVO;
import com.grepiu.www.process.sample.util.socket.module.domain.WatchGradeVO;
import com.grepiu.www.process.sample.util.socket.module.pool.SocketHelper;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Lists;

public class GenreSearch<T1 extends GenerSearchBody, T2 extends GenerSearchVO> extends SejongSocket<T1, T2> {

  public GenreSearch(String code) {
    super(code);
  }

  @Override
  public void send(T1 data) throws Exception {

    StringBuilder sb = new StringBuilder();
    String sample = sb.append(header)
        .append(data.getBeginDate())
        .append(data.getEndDate())
        .toString();
    this.response = SocketHelper.sendDataStream(sample.getBytes("KSC5601"));
  }

  @Override
  public List<T2> response(Class<T2> type) throws Exception {
    // 헤더 Set
    this.header = response.substring(0, response.indexOf(delimiter) + 1);
    // 데이터 Set
    String d = response.substring(header.length(), response.length()) + delimiter;

    this.data = Lists.newArrayList();

    int pos = 0, end, index = 1;
    T2 j = type.getDeclaredConstructor().newInstance();

    while ((end = d.indexOf(delimiter, pos)) >= 0) {
      if (index % 2 == 1) {
        if (index > 1) {
          this.data.add(j);
        }
        j = type.getDeclaredConstructor().newInstance();
        j.setName(d.substring(pos, end));
      } else {
        j.setCode(d.substring(pos, end));
      }
      pos = end + 1;
      index++;
      if (pos > d.lastIndexOf(delimiter)) {
        this.data.add(j);
      }
    }
    return this.data;
  }
}
