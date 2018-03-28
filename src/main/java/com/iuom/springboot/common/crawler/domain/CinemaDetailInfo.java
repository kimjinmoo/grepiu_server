package com.iuom.springboot.common.crawler.domain;

import lombok.Data;

/**
 *
 * 상세정보
 *
 */
@Data
public class CinemaDetailInfo {
    private String movieName;
    private String room;
    private String seat;
    private String time;
}
