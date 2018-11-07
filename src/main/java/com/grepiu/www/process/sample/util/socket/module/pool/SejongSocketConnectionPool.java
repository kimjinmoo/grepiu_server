package com.grepiu.www.process.sample.util.socket.module.pool;

import java.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 커넥션 Pool
 *
 */
public class SejongSocketConnectionPool implements SocketConnectionPool {

  static final Logger logger = LoggerFactory.getLogger(SejongSocketConnectionPool.class);

  private SocketConnectionFactory connectionFactory;

  private String HOST;
  private int PORT;
  private int MAX_SIZE;
  private int MIN_SIZE;

  private Vector<SocketConnection> connections = null;

  public SejongSocketConnectionPool(SocketConnectionFactory connectionFactory, String HOST, int PORT, int MAX_SIZE, int MIN_SIZE) {
    this.connectionFactory = connectionFactory;
    this.HOST = HOST;
    this.PORT = PORT;
    this.MAX_SIZE = MAX_SIZE;
    this.MIN_SIZE = MIN_SIZE;
  }

  public void setConnectionFactory(SocketConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Override
  public synchronized SocketConnection getConnection() {
    SocketConnection result = null;

    //traversal the pool to get free connections
    for (SocketConnection connection : connections) {
      if (connection != null) {
        if (!connection.isBusy()) {
          result = connection;
          logger.info("커넥션 활동됨");
          break;
        }
      } else {
        connection = connectionFactory.createConnection(HOST, PORT);
        logger.info("신규 커넥션을 추가");
        result = connection;
      }
    }
    if (result == null) {
      // max size보다 pool 이 작은경우 생성, 아닌경우 wait 또는 재시도

      if (connections.size() < MAX_SIZE) {
        // 커넥션 재시도
        SocketConnection newConnection = connectionFactory.createConnection(HOST, PORT);
        connections.addElement(newConnection);
        logger.info("빈 커넥션이 없으 신규 커넥션 생성");
        result = newConnection;
      } else {
        throw new RuntimeException("비어있는 커넥션이 존재하지 않습니다.");
      }
    }
    logger.info("현재 커넥션 카운트 " + connections.size());
    result.setBusy(true);
    return result;
  }

  @Override
  public void init() {
    connections = new Vector<SocketConnection>();
    for (int i = 0; i < MIN_SIZE; i++) {
      connections.addElement(connectionFactory.createConnection(HOST, PORT));
    }
    logger.info("초기화, 신규 커넥션 카운트 : " + connections.size());
  }

  @Override
  public void restart() {
    destroy();
    init();
  }

  @Override
  public void destroy() {
    for (SocketConnection connection : connections) {
      if (connection != null) {
        connection.destroy();
        connection = null;
      }
    }
  }

}
