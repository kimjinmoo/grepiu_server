package com.grepiu.www.process.sample.util.socket.module.pool;

/**
 *
 * 커넥션 Factory
 *
 */
public interface SocketConnectionFactory {
  public abstract SocketConnection createConnection(String host, int port);
}
