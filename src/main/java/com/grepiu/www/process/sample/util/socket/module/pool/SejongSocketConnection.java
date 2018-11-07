package com.grepiu.www.process.sample.util.socket.module.pool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 세종 소켓 커넥션
 *
 */
public class SejongSocketConnection implements SocketConnection {

  static final Logger logger = LoggerFactory.getLogger(SejongSocketConnection.class);

  private Socket socket;

  private DataInputStream in;
  private DataOutputStream out;

  private String host;
  private int port;

  // 버퍼
  private int buffer = 1024;

  private boolean busy = false;

  public SejongSocketConnection(String host, int port) throws IOException {
    this.host = host;
    this.port = port;
    this.socket = new Socket(this.host, this.port);
    this.in = new DataInputStream(socket.getInputStream());
    this.out = new DataOutputStream(socket.getOutputStream());
  }

  public boolean isBusy() {
    return busy;
  }

  public void setBusy(boolean busy) {
    this.busy = busy;
  }

  @Override
  public void sendData(byte[] data) throws IOException {
    if (this.socket == null || this.socket.isClosed()) {
      this.socket = new Socket(this.host, this.port);
      this.socket.setSoTimeout(5000);
      this.in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
    }
    out.write(data);
    out.flush();
  }

  @Override
  public String receiveData() throws IOException {
    String result = null;

    byte[] bytes = new byte[buffer];
    int len = in.read(bytes);

    result = new String(bytes, 0, len, "KSC5601");
    return result;
  }

  public void close() {
    this.busy = false;
  }

  @Override
  public void destroy() {
    if (this.in != null) {
      try {
        this.in.close();
      } catch (IOException e) {
        logger.info("inputstream 종료 오류 : " + e.getMessage());
      }
    }
    if (this.out != null) {
      try{
        this.out.close();
      } catch (IOException e) {
        logger.info("outputstream 종료 오류 : " + e.getMessage());
      }

    }
    if (this.socket != null) {
      try {
        this.socket.close();
      } catch (IOException e) {
        logger.info("socket 종료 error " + e.getMessage());
      }
    }
    this.out = null;
    this.in = null;
    this.socket = null;
  }
}
