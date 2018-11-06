package com.grepiu.test.application.socket.module.model;

import java.net.Socket;

/**
 *
 * 싱글 톤 소켓 커넥션
 *
 */
public class SocketConnection {
  // IP
  private static String ip = "127.0.0.1";
  // PORT
  private static int port = 9090;
  // 가시성을 위해 volatile 사용
  private static volatile SocketConnection socketConnection;
  // 소켓
  private static Socket socket;
  private SocketConnection() {
    if(socketConnection != null) {
      throw new RuntimeException("init getInstance");
    }
  }

  public static SocketConnection getInstance() throws Exception {
    if(socketConnection == null) {
      synchronized (SocketConnection.class) {
        if(socketConnection == null) {
          socketConnection = new SocketConnection();
          socket = new Socket(ip, port);
        }
      }
    }
    return socketConnection;
  }

  public Socket getSocket() {
    return socket;
  }
}
