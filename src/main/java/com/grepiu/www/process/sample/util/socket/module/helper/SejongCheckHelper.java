package com.grepiu.www.process.sample.util.socket.module.helper;

import com.grepiu.www.process.sample.util.socket.module.exception.SejongErrorCodeException;
import java.util.Arrays;
import java.util.Optional;

public class SejongCheckHelper {

  /**
   *
   * 성공 여부를 판단한다.
   *
   * @param responseHeader
   * @return
   * @throws Exception
   */
  public static boolean isSuccess(String responseHeader) throws SejongErrorCodeException {
    String responseCode = responseHeader;
    Optional<SejongError> err = Arrays.stream(SejongError.values())
        .filter(e -> e.getCode().equals(responseCode)).findFirst();
    if (err.isPresent()) {
      throw new SejongErrorCodeException(err.get().getCode(), err.get().getMessage());
    }
    return true;
  }
}
