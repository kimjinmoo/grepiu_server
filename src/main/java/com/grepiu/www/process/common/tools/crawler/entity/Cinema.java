package com.grepiu.www.process.common.tools.crawler.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * 시네마 도메인
 *
 */
@Data
@Document(collection="cinema")
public class Cinema implements Serializable {

    @Id
    private String id;
    @Indexed
    private String sido; // 시도

    private String type; // 영화관 타입, 롯데 lotte, CGV cgv

    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,style = "yyyy/MM/dd")
    private Date createDate; // 시간
    private HashMap<String, Object> movieInfo; // 영화 정보
}
