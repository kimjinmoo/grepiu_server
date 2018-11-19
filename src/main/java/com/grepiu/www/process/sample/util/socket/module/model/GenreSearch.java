package com.grepiu.www.process.sample.util.socket.module.model;

import com.grepiu.www.process.sample.util.socket.module.pool.SocketHelper;
import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.List;

public class GenreSearch<T1 extends GenerSearchBody> extends SejongSocket<T1> {

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


    public List<GenerSearchVO> response() {
        // 헤더 Set
        this.header = response.substring(0, response.indexOf(delimiter) + 1);
        // 데이터 Set
        String d = response.substring(header.length(), response.length()) + delimiter;

        List<GenerSearchVO> lists = new ArrayList<>();

        int pos = 0, end, index = 1;
        GenerSearchVO j = null;

        while ((end = d.indexOf(delimiter, pos)) >= 0) {
            if (index % 2 == 1) {
                if (index > 1) {
                    lists.add(j);
                }
                j = new GenerSearchVO();
                j.setName(d.substring(pos, end));
            } else {
                j.setCode(d.substring(pos, end));
            }
            pos = end + 1;
            index++;
            if (pos > d.lastIndexOf(delimiter)) lists.add(j);
        }
        return lists;
    }
}
