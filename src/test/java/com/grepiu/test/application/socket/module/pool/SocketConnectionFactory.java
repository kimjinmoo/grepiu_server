package com.grepiu.test.application.socket.module.pool;

/**
 *
 * 커넥션 Factory
 *
 */
public interface SocketConnectionFactory {
  public abstract SocketConnection createConnection(String host, int port);
}
