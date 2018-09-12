package com.grepiu.www.process.common.api.exception;

/**
 *
 * 벨리데이션 에러 처리
 *
 */
public class BadRequestException extends Exception {

  public BadRequestException(String message) {
    super(message);
  }

  /**
   *
   * Default Return Error
   *
   */
  public BadRequestException() {
    super("잘못된 요청을 하였습니다.");
  }
}
