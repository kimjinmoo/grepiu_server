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
    this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
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
      int length = 50000;
      while(bos.toByteArray().length <= 50000) {
        int bytesRead = 0;
        int bytesToRead = Constant.FILE_DEFAULT_BUFFER;
        System.out.println("buffer : "+buffer.length);
        while (bytesRead < bytesToRead) {
          System.out.println("bytesRead:"+bytesRead);
          int result = in.read(buffer, bytesRead, bytesToRead - bytesRead);
          if (result == -1) break; //1024이하의 스트림을 읽고 끝났을 때의 상황을 고려해야 한다.
          bytesRead +=  result;
        }
        bos.write(buffer);
      }
      bos.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
//    bos.close();
    return bos.toByteArray();
  }

  public int indexOf(byte[] outerArray, byte[] smallerArray) {
    for(int i = 0; i < outerArray.length - smallerArray.length+1; ++i) {
      boolean found = true;
      for(int j = 0; j < smallerArray.length; ++j) {
        if (outerArray[i+j] != smallerArray[j]) {
          found = false;
          break;
        }
      }
      if (found) return i;
    }
    return -1;
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
