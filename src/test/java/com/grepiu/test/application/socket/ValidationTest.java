package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.model.SejongMap;
import com.grepiu.www.process.sample.util.socket.module.pool.SejongStringValidation;
import com.grepiu.www.process.sample.util.socket.module.pool.ValidationResult;

public class ValidationTest {

  public static void main(String...args) {
//    // Text
//    String text = "텍스트";
//    SejongStringValidation validation = SejongStringValidation.isEmpty();
//    validation.apply(text).isSuccess();
//
//    // 날짜
//    String time = "20181112";
//    SejongStringValidation datetime = SejongStringValidation.isDateYYYYMMDD();
//    System.out.println("date : " + datetime.apply(time).isSuccess());

    // 일주일 체크
    SejongStringValidation d = SejongStringValidation.isWithinWeek();
    System.out.println(d.apply("20181209").isSuccess());


  }
}
