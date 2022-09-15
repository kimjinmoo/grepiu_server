package com.grepiu.test.ex.develop;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * 람다 정리
 * ref : https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
 *
 */
public class LambdaSummary {

  public static void main(String...args) {

    // 커스텀 익명 함수 구현 String
    Greet greet = (s) -> {
      String gt = s+"님 안녕하세요";
      System.out.println(gt);
      return gt;
    };
    // 커스텀 익명 함수 구현 void
    Sound sound = (s) -> {
      System.out.println(s);
    };

    // Step1. 실행하기
    greet.name("김진무");
    sound.get("oops");

    // Java 제공 되는 함수형 인터페이스
    // Step2. Functions T->R로 전환
    Function<String, Integer> function = (o) -> {
      return Integer.valueOf(o)*2;
    };

    List<String> sample = Arrays.asList("1","2");
    System.out.println("sample : "+ sample);
    List<Integer> o = sample.stream().map(function).collect(Collectors.toList());
    System.out.println("sample : "+ o);

    // Step3. Predicated boolean 리턴
    Predicate predicate = (o1) -> {
      return Optional.ofNullable(o1).isPresent();
    };

    System.out.println("result : " + predicate.test(null));


    // Step4. Consumers
    Consumer<String> consumer = (v)->{
      System.out.println(v);
    };

    consumer.accept("헤엘로");

  }
}
@FunctionalInterface
interface Greet {
  String name(String str);
}

@FunctionalInterface
interface Sound {
  void get(String str);
}
