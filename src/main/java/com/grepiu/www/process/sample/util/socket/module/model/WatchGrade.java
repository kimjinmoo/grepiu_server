package com.grepiu.www.process.sample.util.socket.module.model;

import com.grepiu.www.process.sample.util.socket.module.pool.SocketHelper;
import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * 관람 등급
 */
public class WatchGrade<T1 extends WatchGradeBody> extends SejongSocket<T1> {

    public WatchGrade(String code) {
        super(code);
    }

    @Override
    public void send(T1 data) throws Exception {
        StringBuilder sb = new StringBuilder();
        String sample = sb.append(header)
                .append(data.getCost())
                .append(data.getType())
                .toString();
        this.response = SocketHelper.sendDataStream(sample.getBytes("KSC5601"));
    }

    public List<WatchGradeVO> response() {
        // 헤더 Set
        this.header = response.substring(0, response.indexOf(delimiter) + 1);
        // 데이터 Set
        String d = response.substring(header.length(), response.length()) + delimiter;

        List<WatchGradeVO> lists = Lists.newArrayList();

        int pos = 0, end, index = 1, route = 3;
        WatchGradeVO j = null;

        while ((end = d.indexOf(delimiter, pos)) >= 0) {
            switch (index) {
                case 1:
                    j = new WatchGradeVO();
                    j.setCode(d.substring(pos, end));
                    break;
                case 2:
                    j.setName(d.substring(pos, end));
                    break;
                case 3:
                    j.setCost(d.substring(pos, end));
                    lists.add(j);
                    index = 0;
                    break;
            }
            index++;
            pos = end + 1;
        }
        return lists;
    }
}
