package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.model.SejongMap;
import com.grepiu.www.process.sample.util.socket.module.pool.SejongStringValidation;
import com.grepiu.www.process.sample.util.socket.module.pool.ValidationResult;

public class ValidationTest {

  public static void main(String...args) {
    String text = "201811";
    SejongStringValidation validation = SejongStringValidation.isEmpty();
    validation.apply(text).getReason().ifPresent(System.out::println);

    String time = "fsfd";
    SejongStringValidation datetime = SejongStringValidation.isDateYYYYMMDD();
    System.out.println("date : " + datetime.apply(time).isValid());

  }
}
