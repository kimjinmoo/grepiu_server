package com.iuom.springboot.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * 날짜관련 유틸리티
 * v1.0
 *
 */
public class DateUtils {

    /**
     *
     * 현재 시간을 가져온다.
     *
     * @param formatter DateTimeFormatter 객체
     * @return String 객체
     */
    public static String now(DateTimeFormatter formatter){
        LocalDateTime localData = LocalDateTime.now();
        return formatter.format(localData);
    }

    /**
     *
     * 현재 시간을 패턴형식으로 가져온다.
     *
     * @param patten
     * @return
     */
    public static String now(String patten) {
        LocalDateTime localDate = LocalDateTime.now();

        return DateTimeFormatter.ofPattern(patten).format(localDate);
    }
}
