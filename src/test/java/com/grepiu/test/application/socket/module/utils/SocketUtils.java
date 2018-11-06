package com.grepiu.test.application.socket.module.utils;

import com.grepiu.test.application.socket.SocketConnect;
import com.grepiu.test.application.socket.module.model.SocketConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketUtils {
  // 받는 데이터 버퍼 Set
  private static final int DEFAULT_BUFFER = 1024;

  public static String sendDataStream(byte[] data, int buffer) throws Exception {
    DataOutputStream outputStream = null;
    DataInputStream inputStream = null;
    try {
      SocketConnection socketConnection = SocketConnection.getInstance();

      outputStream = new DataOutputStream(socketConnection.getSocket().getOutputStream());
      inputStream = new DataInputStream(socketConnection.getSocket().getInputStream());
      outputStream.write(data);
      outputStream.flush();

      byte[] in = new byte[buffer];
      inputStream.read(in, 0, in.length);
      return new String(in, 0, in.length, "KSC5601").trim();
    } catch (Exception e) {
      throw e;
    }
  }

  // 소켓 커넥션 연결
  public static String sendDataStream(byte[] data) throws Exception {
    return sendDataStream(data, DEFAULT_BUFFER);
  }
}
