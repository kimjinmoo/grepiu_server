package com.grepiu.www.process.common.api.domain;

import lombok.Data;

/**
 *
 * 응답
 *
 */
@Data
public class Response {

  private int code;
  private String message;

  public Response(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public Response() {
  }
}
