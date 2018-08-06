package com.grepiu.www.process.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * 날짜관련 유틸리티
 * v1.0
 *
 */
public class DateUtil {

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

    /**
     *
     * 시간을 스트링으로 변경한다.
     *
     * @param patten
     * @return
     */
    public static String toString(Date date, String patten) {
        DateFormat df = new SimpleDateFormat(patten);
        return df.format(date);
    }
}
