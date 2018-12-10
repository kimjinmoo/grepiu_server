package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.model.SejongMap;
import com.grepiu.www.process.sample.util.socket.module.pool.SejongStringValidation;
import com.grepiu.www.process.sample.util.socket.module.pool.ValidationResult;

public class ValidationTest {

  public static void main(String...args) {
    String datatime = "text";
    SejongStringValidation validation = SejongStringValidation.isEmpty();
    ValidationResult result = validation.apply(datatime);
    result.getReason().ifPresent(System.out::println);
  }
}
