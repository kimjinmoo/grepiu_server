package com.grepiu.test.ex;

import com.google.common.base.Function;
import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.commons.lang3.RandomStringUtils;

public class IfVsSwich {

  public static void main(String...args) {
    int loop = 100000000*9;
    Consumer<Integer> test = v -> {
      int a = v;
      int b = 1;
      int sum = a+b;
    };
    // first
    randomIfElse("Z", loop, test);
    randomIfElseTwo("Z", loop, test);
    randomIfElseFive("Z", loop, test);
    randomIfNotElseFive("Z", loop, test);
    randomAccessSwitchCaseTwo("Z", loop, test);
    randomAccessSwitchCaseFive("Z", loop, test);
    randomAccessSwitchCaseTen("Z", loop, test);
    System.out.println("Next Case : next word");
    // next word
    randomIfElse("A", loop, test);
    randomIfElseTwo("A", loop, test);
    randomIfElseFive("A", loop, test);
    randomIfNotElseFive("A", loop, test);
    randomAccessSwitchCaseTwo("A", loop, test);
    randomAccessSwitchCaseFive("A", loop, test);
    randomAccessSwitchCaseTen("A", loop, test);
    System.out.println("Next Case : max word");
    // max word
    randomIfElse("D", loop, test);
    randomIfElseTwo("D", loop, test);
    randomIfElseFive("D", loop, test);
    randomIfNotElseFive("D", loop, test);
    randomAccessSwitchCaseTwo("D", loop, test);
    randomAccessSwitchCaseFive("D", loop, test);
    randomAccessSwitchCaseTen("D", loop, test);
  }

  private static void randomIfElse(String choice, int loop, Consumer<Integer> consumer) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; loop > i; i++) {
      if(choice.equals("Z")) {
        consumer.accept(1);
      }
    }
    System.out.println("randomIfElse :" + stopwatch.elapsed(TimeUnit.SECONDS));
  }

  private static void randomIfElseTwo(String choice, int loop, Consumer<Integer> consumer) {

    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; loop > i; i++) {
      if(choice.equals("Z")) {
        consumer.accept(1);
      } else if(choice.equals("A")) {
        consumer.accept(1);
      }
    }
    System.out.println("randomIfElseTwo :" + stopwatch.elapsed(TimeUnit.SECONDS));
  }

  private static void randomIfElseFive(String choice, int loop, Consumer<Integer> consumer) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; loop > i; i++) {
      if(choice.equals("Z")) {
        consumer.accept(1);
      } else if(choice.equals("A")) {
        consumer.accept(1);
      } else if(choice.equals("B")) {
        consumer.accept(1);
      } else if(choice.equals("C")) {
        consumer.accept(1);
      } else if(choice.equals("D")) {
        consumer.accept(1);
      }
    }
    System.out.println("randomIfElseFive :" + stopwatch.elapsed(TimeUnit.SECONDS));
  }

  private static void randomIfNotElseFive(String choice, int loop, Consumer<Integer> consumer) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; loop > i; i++) {
      if(choice.equals("Z")) {
        consumer.accept(1);
      }
      if(choice.equals("A")) {
        consumer.accept(1);
      }
      if(choice.equals("B")) {
        consumer.accept(1);
      }
      if(choice.equals("C")) {
        consumer.accept(1);
      }
      if(choice.equals("D")) {
        consumer.accept(1);
      }
    }
    System.out.println("randomIfNotElseFive :" + stopwatch.elapsed(TimeUnit.SECONDS));
  }

  private static void randomAccessSwitchCaseTwo(String choice, int loop, Consumer<Integer> consumer) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; loop > i; i++) {
      switch (choice) {
        case "Z":
          consumer.accept(1);
          break;
        case "A":
          consumer.accept(1);
          break;
      }
    }
    System.out.println("randomAccessSwitchCaseTwo :" + stopwatch.elapsed(TimeUnit.SECONDS));
  }

  private static void randomAccessSwitchCaseFive(String choice, int loop, Consumer<Integer> consumer) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; loop > i; i++) {
      switch (choice) {
        case "Z":
          consumer.accept(1);
          break;
        case "A":
          consumer.accept(1);
          break;
        case "B":
          consumer.accept(1);
          break;
        case "C":
          consumer.accept(1);
          break;
        case "D":
          consumer.accept(1);
          break;
      }
    }
    System.out.println("randomAccessSwitchCaseFive :" + stopwatch.elapsed(TimeUnit.SECONDS));
  }

  private static void randomAccessSwitchCaseTen(String choice, int loop, Consumer<Integer> consumer) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; loop > i; i++) {
      switch (choice) {
        case "Z":
          consumer.accept(1);
          break;
        case "A":
          consumer.accept(1);
          break;
        case "B":
          consumer.accept(1);
          break;
        case "C":
          consumer.accept(1);
          break;
        case "D":
          consumer.accept(1);
          break;
        case "E":
          consumer.accept(1);
          break;
        case "F":
          consumer.accept(1);
          break;
        case "G":
          consumer.accept(1);
          break;
        case "H":
          consumer.accept(1);
          break;
        default:
          consumer.accept(1);
          break;
      }
    }
    System.out.println("randomAccessSwitchCaseTen :" + stopwatch.elapsed(TimeUnit.SECONDS));
  }
}
