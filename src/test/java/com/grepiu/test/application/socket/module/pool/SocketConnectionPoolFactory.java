package com.grepiu.test.application.socket.module.pool;

/**
 *
 * 커넥션 Pool Factory
 *
 */
public interface SocketConnectionPoolFactory {

  // 커넥션을 생성한다.
  public abstract SocketConnectionPool createConnectionPool();

  // 커넥션을 생선한다.
  public abstract SocketConnectionPool createConnectionPool(String host, int port, int maxSize, int minSize);
}
