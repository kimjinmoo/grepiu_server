package com.grepiu.www.process.sample.util.socket.module.pool;

import com.grepiu.www.process.sample.util.socket.module.model.SejongMap;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

  static SejongStringValidation isSeatType(){
    return holds(v->{
      return (v.indexOf("1") > -1 || v.indexOf("2") > -1);
    },"잘못된 값입니다.");
  }

  static SejongStringValidation isWithinWeek() {
    return holds(v->{
      try{
        DateTimeFormatter fmt = DateTimeFormatter.BASIC_ISO_DATE;
        LocalDate searchDate = LocalDate.parse(v, fmt);
        LocalDate nowDate  = LocalDate.now();
        LocalDate plusWeekDate  = LocalDate.now().plusWeeks(1);

        return !searchDate.isBefore(nowDate) && !searchDate.isAfter(plusWeekDate);
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }, "범위가 초과 되었습니다.");
  }

  static SejongStringValidation holds(Predicate<String> p, String message) {
    return d -> {
      if(!p.test(d)) {
        throw new RuntimeException(message);
      }
      return p.test(d) ? new ValidationResult(true, message) : new ValidationResult(false, message);
    };
  }

  default SejongStringValidation and(SejongStringValidation other) {
    return v -> {
      final ValidationResult result = this.apply(v);
      return result.isSuccess() ? other.apply(v) : result;
    };
  }
}
