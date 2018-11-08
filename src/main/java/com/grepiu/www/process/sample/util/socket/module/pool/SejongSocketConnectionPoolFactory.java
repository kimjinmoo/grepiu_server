package com.grepiu.www.process.sample.util.socket.module.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SejongSocketConnectionPoolFactory {

  static final Logger logger = LoggerFactory.getLogger(SejongSocketConnectionPoolFactory.class);

  public SejongSocketConnectionPool createConnectionPool() {
    SejongSocketConnectionFactory connectionFactory = new SejongSocketConnectionFactory();

    SejongSocketConnectionPool socketConnectionPool = new SejongSocketConnectionPool(connectionFactory);
    socketConnectionPool.init();
    return socketConnectionPool;
  }
}
