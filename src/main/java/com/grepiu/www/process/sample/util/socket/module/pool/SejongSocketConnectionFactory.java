package com.grepiu.www.process.sample.util.socket.module.pool;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SejongSocketConnectionFactory {

  static final Logger logger = LoggerFactory.getLogger(SejongSocketConnectionFactory.class);

  public SejongSocketConnection createConnection() {
    SejongSocketConnection connection = null;
    try {
      connection = new SejongSocketConnection();
    } catch (IOException e) {
      logger.error("신규 커넥션 생성 오류 : {}", e.getMessage());
    }
    return connection;
  }
}
