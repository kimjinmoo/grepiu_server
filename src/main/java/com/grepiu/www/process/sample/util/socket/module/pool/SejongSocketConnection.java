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

  private DataInputStream in;
  private DataOutputStream out;
//  private FileOutputStream outFile;
  private ByteArrayOutputStream bos;


  private boolean busy = false;

  public SejongSocketConnection() throws IOException {
    this.socket = null;
    this.in = null;
    this.out = null;
  }

  public void connect() throws Exception {
    this.socket = new Socket(Constant.DEFAULT_HOST, Constant.DEFAULT_PORT);
    this.in = new DataInputStream(socket.getInputStream());
    this.out = new DataOutputStream(socket.getOutputStream());
  }

  public void connect(String host, int port) throws Exception {
    this.socket = new Socket(host, port);
    this.in = new DataInputStream(socket.getInputStream());
    this.out = new DataOutputStream(socket.getOutputStream());
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
    String result = null;

    byte[] bytes = new byte[Constant.DEFAULT_BUFFER];
    int len = in.read(bytes);

    result = new String(bytes, 0, len, "KSC5601");
    return result;
  }

  public byte[] receiveFileData(byte[] data) throws Exception {
    if (this.socket == null || this.socket.isClosed()) {
      throw new Exception("접속이 되지 않았습니다.");
    }
    Socket sc = new Socket("52.78.158.161",9080);
    Reciver r = new Reciver(sc);
    r.start();
    new Send(sc, data).start();
    while(r.isAlive()){
    }
    return r.getDate();
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
class Send extends Thread {
  private Socket socket;
  private DataOutputStream out;
  private byte[] data;

  public Send(Socket socket, byte[] data) throws Exception {
    this.socket = socket;
    this.data = data;
  }

  @Override
  public void run() {
    try{
      this.out = new DataOutputStream(this.socket.getOutputStream());
      Thread.sleep(1000*2);
      while(this.socket.isConnected()){
        System.out.println("send");
        this.out.write(this.data);
        this.out.flush();
        break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class Reciver extends Thread {
  private Socket socket;
  private DataInputStream in;
  private ByteArrayOutputStream bos;

  public Reciver(Socket socket) throws Exception {
    this.socket = socket;
  }

  @Override
  public void run() {
    try{
      this.in = new DataInputStream(this.socket.getInputStream());
      this.bos = new ByteArrayOutputStream();

      while(!(new String(this.bos.toByteArray()).indexOf("ETX") != -1)) {
        byte[] buffer = new byte[Constant.FILE_DEFAULT_BUFFER];
        // read Firset
        int bytesRead = 0;
        while ((bytesRead = in.read(buffer)) > 0) {
          this.bos.write(buffer, 0, bytesRead);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public byte[] getDate() {
    return this.bos.toByteArray();
  }
}
