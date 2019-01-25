package com.grepiu.test.process.study;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * 기본 람다식
 *
 */
public class LambdaExpressionSample {

  public static void main(String...ars) throws Exception {

    // Java에서 권장 람다 표현식
    // parameter -> parameter
    Consumer<String> ex1 = s -> System.out.println(s);

    // 람다 바디에서 코드 블럭 제거 할 것
    Consumer<String> ex2 = s -> {
      // 코드가 길어 진다.
      System.out.println(s);
    };

    // 싱글 파라미터 괄호 제거
    Consumer<String> ex3 = (s) -> { // 불필요, 사용 할때는 매가 변수가 없거나 2개 이상인 경우 쓰는것이 권장
      System.out.println(s);
    };

    // 중괄호화  리턴은 람다의 옵션, 명료성을 위해 리턴을 뺀다.
    Predicate<String> ex4 = s -> {return s.isEmpty();};

    // 메소드 레퍼런스 이용
//    Optional.ofNullable(new Object()).orElseThrow(Exception::new);
//    Consumer<String> ex1_method_r = System.out::println;
//    Function<Optional<String>, Boolean> ex5 = Optional::isEmpty;

    // java8에 기본적인 functional interface
    // Functional Interface / Parameter Type /   return Type /   Abstract Method Name
    // Runnable                  none                void              run
    // Supplier<T>               none                 T                get
    // Consumer<T>               T                   void              accept
    // BiConsumer<T, U>          T,U                 void              accept
    // Function<T, R>            T                     R               apply
    // BiFunction<T, U, R>       T,U                   R               apply
    // UnaryOperator<T>          T                     T               apply
    // BinaryOperator<T>         T,T                   T               apply
    // Predicate<T>              T                     boolean         test
    // BiPredicate<T, U>         T,U                   boolean         test


  }
}
