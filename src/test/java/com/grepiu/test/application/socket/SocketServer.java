package com.grepiu.test.application.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 소켓 서버
 *
 */
public class SocketServer {

  // 연결할 포트를 지정합니다.
  private static final int PORT = 9090;
  // 스레드 풀의 최대 스레드 개수를 지정합니다.
  private static final int THREAD_CNT = 10;
  private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_CNT);
  public static void main(String[] args) {

    try {
      // 서버소켓 생성
      ServerSocket serverSocket = new ServerSocket(PORT);

      // 소켓서버가 종료될때까지 무한루프
      while(true){
        // 소켓 접속 요청이 올때까지 대기합니다.
        Socket socket = serverSocket.accept();
        try{
          // 요청이 오면 스레드 풀의 스레드로 소켓을 넣어줍니다.
          // 이후는 스레드 내에서 처리합니다.
          threadPool.execute(new ConnectionWrap(socket));
        }catch(Exception e){
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

// 소켓 처리용 래퍼 클래스입니다.
class ConnectionWrap implements Runnable{

  private Socket socket = null;

  public ConnectionWrap(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {

    try {
      // 응답을 위해 스트림을 얻어옵니다.
      DataInputStream in = new DataInputStream(socket.getInputStream());
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());

      byte[] bytes = new byte[1024];
      int len = in.read(bytes);
      System.out.println("받은 데이터 : "+new String(bytes, 0, len, "KSC5601"));

      out.write("SAC!@#0003179102026000010639102103                        103065                                                      0000N000014000000|없음|#000001|뮤지컬|#000003|콘서트|#000004|연극|#000006|가족/아동|#000007|오페라|#000009|전시/체험|#000013|국악|#000014|무용|#000024|클래식|#000999|기타|#001000|발레|#001001|교육|#001002|합창|#".getBytes("KSC5601"));
      out.flush();
      // 클라이언트로부터 메시지 입력받음
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close(); // 반드시 종료합니다.
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
