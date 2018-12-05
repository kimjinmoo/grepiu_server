package com.grepiu.www.process.sample.util.socket.module.pool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 세종 소켓 커넥션
 */
public class SejongSocketConnection {

  static final Logger logger = LoggerFactory.getLogger(SejongSocketConnection.class);

  private final String ETX = "ETX";

  private Socket socket;

  private DataInputStream in;
  private DataOutputStream out;
//  private FileOutputStream outFile;
  private ByteArrayOutputStream bos;

  private boolean busy = false;

  public SejongSocketConnection() throws IOException {
    this.socket = new Socket(Constant.DEFAULT_HOST, Constant.DEFAULT_PORT);
    this.socket.setSoTimeout(1000 * 15);
    this.in = new DataInputStream(socket.getInputStream());
    this.out = new DataOutputStream(socket.getOutputStream());
  }

  public void setFileMode(String host, int port) throws IOException {
    this.destroy();
    this.socket = new Socket(host, port);
    this.socket.setSoTimeout(1000 * 15);
    this.in = new DataInputStream(socket.getInputStream());
    this.out = new DataOutputStream(socket.getOutputStream());
  }

  public boolean isBusy() {
    return busy;
  }

  public void setBusy(boolean busy) {
    this.busy = busy;
  }

  public void sendData(byte[] data) throws IOException {
    if (this.socket == null || this.socket.isClosed()) {
      this.socket = new Socket(Constant.DEFAULT_HOST, Constant.DEFAULT_PORT);
      this.socket.setSoTimeout(5000);
      this.in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
    }
    out.write(data);
    out.flush();
  }

  public String receiveData() throws IOException {
    String result = null;

    byte[] bytes = new byte[Constant.DEFAULT_BUFFER];
    int len = in.read(bytes);

    result = new String(bytes, 0, len, "KSC5601");
    return result;
  }

  public byte[] receiveFileData() throws IOException {
    try {
      bos = new ByteArrayOutputStream();
      byte[] buffer = new byte[Constant.FILE_DEFAULT_BUFFER];
      int bytesRead = 0;
      boolean isSuccess = false;
      while(!isEnd(bos.toByteArray())) {
        System.out.println("loop");
        System.out.println("loop" + in.available());
        int able = in.available();
        if(able > 0 && able <= buffer.length) {
          in.readFully(buffer, 0, able);
        } else {
          in.readFully(buffer, 0, buffer.length);
        }
        bos.write(buffer);
      }
      // 기본적인 파일 다운로드
//      while((bytesRead = in.read(buffer, 0, buffer.length))>0) {
//        System.out.println("available : "+in.available());
//        System.out.println("read byte : "+bytesRead);
//        bos.write(buffer,0, bytesRead);
//      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      bos.flush();
    }
    return bos.toByteArray();
  }

  private boolean isEnd(byte[] bytes) {
    String str = new String(bytes);
    System.out.println(str);
    System.out.println(str.indexOf(Constant.FILE_ETX));
    return str.indexOf(Constant.FILE_ETX) != -1;
  }

  public void close() {
    this.busy = false;
  }

  public void destroy() {
    if (this.in != null) {
      try {
        this.in.close();
      } catch (IOException e) {
        logger.info("inputstream 종료 오류 : {}", e.getMessage());
      }
    }
    if (this.out != null) {
      try {
        this.out.close();
      } catch (IOException e) {
        logger.info("outputstream 종료 Error : {}", e.getMessage());
      }

    }
    if (this.socket != null) {
      try {
        this.socket.close();
      } catch (IOException e) {
        logger.info("socket 종료 Error : {}", e.getMessage());
      }
    }
    if (this.bos != null) {
      try {
        this.bos.close();
      } catch (IOException e) {
        logger.info("FileStream 종료 Error : {}", e.getMessage());
      }
    }
    this.busy = false;
    this.out = null;
    this.bos = null;
    this.in = null;
    this.socket = null;
  }
}
