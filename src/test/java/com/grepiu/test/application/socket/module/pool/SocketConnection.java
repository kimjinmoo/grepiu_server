package com.grepiu.test.application.socket.module.pool;

import java.io.IOException;

/**
 *
 * 소켓 케넥션
 *
 */
public interface SocketConnection {

  public boolean isBusy();

  public void setBusy(boolean busy);

  public void sendData(byte[] data) throws Exception;

  public String receiveData() throws IOException;

  // 닫기
  public void close();

  // 종료
  public void destroy();
}
