package com.grepiu.www.process.grepiu.common;

/**
 *
 * 파일 타입을 지정한다.
 *
 */
public enum CloudAttributeType {

  FILE("F"),  // 파일
  DIRECTORY("D"); // 디렉토리

  String code;

  CloudAttributeType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
