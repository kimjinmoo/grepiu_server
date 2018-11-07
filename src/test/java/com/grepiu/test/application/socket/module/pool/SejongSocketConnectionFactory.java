package com.grepiu.test.application.socket.module.pool;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SejongSocketConnectionFactory implements SocketConnectionFactory {

  static final Logger logger = LoggerFactory.getLogger(SejongSocketConnectionFactory.class);

  @Override
  public SocketConnection createConnection(String host, int port) {
    SocketConnection connection = null;
    try {
      connection = new SejongSocketConnection(host, port);
    } catch (IOException e) {
      logger.error("신규 커넥션 생성 오류 :" + e.getMessage());
    }
    return connection;
  }
}
