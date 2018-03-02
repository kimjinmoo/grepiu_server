package com.iuom.springboot.common.crawler.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * 시네마 도메인
 *
 */
@Data
@Document(collection="cinema")
public class Cinema implements Serializable {

    @Id
    private String _id;
    @Indexed
    private String sido; // 시도
    @Indexed
    private String area; // 지역

    @CreatedDate
    private Date createDate; // 시간
    private List<CinemaDetailInfo> movieInfo; // 영화 정보

}
