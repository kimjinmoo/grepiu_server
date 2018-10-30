package com.grepiu.www.process.common.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketUtils {
  // 받는 데이터 버퍼 Set
  private static final int DEFAULT_BUFFER = 512;

  public static String sendDataStream(final Socket socket, String ip , int port, byte[] data, int buffer) throws Exception {
    DataOutputStream outputStream = null;
    DataInputStream inputStream = null;
    try {
      socket.connect(new InetSocketAddress(ip, port));

      outputStream = new DataOutputStream(socket.getOutputStream());
      inputStream = new DataInputStream(socket.getInputStream());
      outputStream.write(data);
      outputStream.flush();

      byte[] in = new byte[buffer];
      inputStream.read(in, 0, in.length);

      return new String(in, 0, in.length, "KSC5601").trim();

    } catch (Exception e) {
      throw e;
    } finally {
      socket.close();
    }
  }

  // 소켓 커넥션 연결
  public static String sendDataStream(final Socket socket, String ip , int port, byte[] data) throws Exception {
    return sendDataStream(socket, ip, port, data, DEFAULT_BUFFER);
  }
}
