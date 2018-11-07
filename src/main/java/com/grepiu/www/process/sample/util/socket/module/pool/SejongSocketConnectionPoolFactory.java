package com.grepiu.www.process.sample.util.socket.module.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SejongSocketConnectionPoolFactory implements SocketConnectionPoolFactory {

  static final Logger logger = LoggerFactory.getLogger(SejongSocketConnectionPoolFactory.class);

  private static final String DEFAULT_HOST = "127.0.0.1";
  private static final int DEFAULT_PORT = 9090;
  private static final int DEFAULT_MAX_SIZE = 20;
  private static final int DEFAULT_MIN_SIZE = 10;

  @Override
  public SocketConnectionPool createConnectionPool() {
    return this.createConnectionPool(DEFAULT_HOST, DEFAULT_PORT, DEFAULT_MAX_SIZE, DEFAULT_MIN_SIZE);
  }

  @Override
  public SocketConnectionPool createConnectionPool(String host, int port, int maxSize, int minSize) {
    SocketConnectionFactory connectionFactory = new SejongSocketConnectionFactory();

    SocketConnectionPool socketConnectionPool = new SejongSocketConnectionPool(connectionFactory, host, port, maxSize, minSize);
    socketConnectionPool.init();
    return socketConnectionPool;
  }
}
