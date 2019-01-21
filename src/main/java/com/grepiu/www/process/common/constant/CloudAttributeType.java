package com.grepiu.www.process.common.constant;

/**
 *
 * 파일 타입을 지정한다.
 *
 */
public enum CloudAttributeType {
  FILE("F"),
  DIRECTORY("D");

  String code;

  CloudAttributeType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
