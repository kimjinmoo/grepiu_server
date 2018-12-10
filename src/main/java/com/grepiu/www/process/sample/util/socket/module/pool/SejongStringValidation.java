package com.grepiu.www.process.sample.util.socket.module.pool;

import com.grepiu.www.process.sample.util.socket.module.model.SejongMap;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Predicate;

public interface SejongStringValidation extends Function<String, ValidationResult> {

  static SejongStringValidation isEmpty() {
    return holds(v -> {
      System.out.println(v);
      return !v.isEmpty();
    }
    ,"데이터가 비어 있습니다.");
  }

  static SejongStringValidation isDateYYYYMMDD() {
    return holds(v->{
      try {
        new SimpleDateFormat("yyyyMMdd").parse(v);
        return true;
      } catch (Exception e) {
        return false;
      }
    },"날짜 형식이 잘못 되었습니다.");
  }

  static SejongStringValidation holds(Predicate<String> p, String message){
    return d -> p.test(d) ? new ValidationResult(message, true) : new ValidationResult(message, false);
  }

  default SejongStringValidation and(SejongStringValidation other) {
    return v -> {
      final ValidationResult result = this.apply(v);
      return result.isSuccess() ? other.apply(v) : result;
    };
  }
}
