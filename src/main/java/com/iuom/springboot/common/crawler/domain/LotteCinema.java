package com.iuom.springboot.common.crawler.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 롯데 시네마 도메인
 *
 */
@Data
@Document(collection="lottecinema")
public class LotteCinema implements Serializable {

    @Indexed
    private String sido; // 시도
    private String area; // 지역
    private List<Movie> movieInfo; // 영화 정보
    private String createDate; // 시간

    @Data
    public class Movie {
        private String movieName;
        private String room;
        private String seat;
        private String time;
    }
}
