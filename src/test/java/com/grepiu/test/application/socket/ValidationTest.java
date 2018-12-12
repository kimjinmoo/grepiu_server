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
    SejongStringValidation sv = SejongStringValidation.isEmpty().and(SejongStringValidation.isDateYYYYMMDD()).and(SejongStringValidation.isWithinWeek());
    ValidationResult r = sv.apply("20171211");
//    System.out.println(sv.apply("20171211").getOnErrorMsg());
    // 일주일 체크
//    SejongStringValidation d = SejongStringValidation.isWithinWeek();
//    System.out.println(d.apply("20181101").isSuccess());


  }
}
