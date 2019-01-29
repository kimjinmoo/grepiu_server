package com.grepiu.www.process.sample.util.socket.module.pool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 세종 소켓 커넥션
 */
public class SejongSocketConnection {

  static final Logger logger = LoggerFactory.getLogger(SejongSocketConnection.class);

  private Socket socket;

  // Stream
  private DataInputStream in;
  private DataOutputStream out;
  private ByteArrayOutputStream bos;

  // Thread
  private SejongFileReceiver receiver;
  private SejongFileTextSender sender;

  // 상태
  private boolean busy = false;

  private int current_loop = 0;

  public SejongSocketConnection() throws IOException {
    this.socket = null;
    this.in = null;
    this.out = null;
    this.current_loop = 0;
  }

  public void connect() throws Exception {
    this.socket = new Socket(Constant.DEFAULT_HOST, Constant.DEFAULT_PORT);
    this.in = new DataInputStream(socket.getInputStream());
    this.out = new DataOutputStream(socket.getOutputStream());
  }

  public void connect(String host, int port) throws Exception {
    this.socket = new Socket(host, port);
  }

  public boolean isBusy() {
    return busy;
  }

  public void setBusy(boolean busy) {
    this.busy = busy;
  }

  public void sendData(byte[] data) throws Exception {
    if (this.socket == null || this.socket.isClosed()) {
      throw new Exception("접속이 되지 않았습니다.");
    }
    out.write(data);
    out.flush();
  }

  public String receiveData() throws IOException {
    String result;
    try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      int read = 0;
      byte[] bytes = new byte[Constant.DEFAULT_BUFFER];
      while((read = in.read(bytes)) != -1) {
        bos.write(bytes, 0, read);
      }
      result = new String(bos.toByteArray(), "KSC5601");
    }
    return result;
  }

  public byte[] receiveFileData(byte[] data) throws Exception {
    if (this.socket == null || this.socket.isClosed()) {
      throw new Exception("접속이 되지 않았습니다.");
    }
    // Set 쓰레드
    this.receiver = new SejongFileReceiver(this.socket);
    this.receiver.start();
    this.sender = new SejongFileTextSender(this.socket, data);
    this.sender.start();
    // Loop 체크 , 시간 초과시 받은것만 리턴
    while (Constant.MAX_LOOP_LIMIT > current_loop && this.receiver.isAlive()) {
      current_loop++;
      Thread.sleep(100);
    }

    logger.info("total : {}", this.receiver.getDate().length);
    return current_loop > Constant.MAX_LOOP_LIMIT ? "".getBytes() : this.receiver.getDate();
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
    if(this.receiver != null && this.receiver.isAlive()) {
      this.receiver.interrupt();
    }
    if(this.sender != null && this.sender.isAlive()) {
      this.sender.interrupt();
    }
    // set 상태
    this.busy = false;
    this.current_loop = 0;
    // Set 스티림
    this.out = null;
    this.bos = null;
    this.in = null;
    // Set 쓰레드
    this.receiver = null;
    this.sender = null;
    // Set 소켓
    this.socket = null;
  }
}
