package com.grepiu.test.application.socket;

import java.util.stream.IntStream;

public class CheckVO {

  public static void main(String...ars)  {
      System.out.println("["+isN(4,"1")+"]");
    System.out.println("["+isX(5,"1")+"]");
  }

  /**
   * 숫자형일경우 0 자릿수 만큼 만듦
   * @param length
   * @param n
   * @return
   */
  static String isN(int length, String n) {
    try {
      if(length < n.length()) {
        throw new RuntimeException("자리수가 초과 하였습니다.");
      }
      StringBuilder str = new StringBuilder();
      IntStream.range(0, length-n.length()).forEach(v->{
        str.append(0);
      });
      str.append(n);

      return str.toString();
    } catch (Exception e) {
     return e.getMessage();
    }
  }

  /**
   *
   * 문자형일 경우 Space 자릿수 만큼 만듦
   * @param length
   * @param n
   * @return
   */
  static String isX(int length, String n) throws RuntimeException {
    if(length < n.length()) {
      throw new RuntimeException("자리수가 초과 하였습니다.");
    }
    StringBuilder str = new StringBuilder();
    str.append(n);
    IntStream.range(0, length-n.length()).forEach(v->{
      str.append(" ");
    });
    return str.toString();
  }
}
