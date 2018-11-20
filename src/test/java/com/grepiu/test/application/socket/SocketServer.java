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
      String test = "SAC!@#0003179102026000010639102103                        103065                                                      0000N000014000000|없음|#000001|뮤지컬|#000003|콘서트|#000004|연극|#000006|가족/아동|#000007|오페라|#000009|전시/체험|#000013|국악|#000014|무용|#000024|클래식|#000999|기타|#001000|발레|#001001|교육|#001002|합창|#";
      String test1= "102103|10000491|T_시크릿가든|000024|120|Y|#102103|10001339|(테스트)T_카카오_S씨어터|000004|120|Y|#102103|10001342|테스트 <공연> - 신청|000000||Y|#102103|10000544|T_양재웅 피아노 독주회|000024|90|Y|#102103|10000612|T_2016서울스프링실내악축제<Grandioso>|000024|100|Y|#102103|10001275|(테스트)T_디에고리베라 프라이드 오브 멕시코|000009||Y|#102103|10001307|T_뮤지컬 갈라 콘서트|000001|120|Y|#102103|10000775|T_서울콘서트필하모닉오케스트라와 함께하는 송년음악회|000024|105|Y|#102103|10001119|T_박초희 귀국 피아노 독주회(테스트)|000024|90|Y|#102103|10001332|(T테스트) 공연등록테스트|000000|120|Y|#102103|10001341|(테스트)T_카카오_미술관|000009||Y|#102103|10001344|(테스트)T_S씨어터_가변1형|000000|120|Y|#102103|10000911|T_2017 세종문화회관 신년음악회|000024|100|Y|#102103|10001335|테스트 <공연> - 신청|000000||Y|#102103|10001343|테스트 <공연> - 신청|000000||Y|#102103|10000751|T_메조소프라노 변지현 귀국독창회|000024|90|Y|#102103|10000722|T_초콜릿 콘서트_세상에서 가장 아름다운 발렌타인데이|000024||Y|#102103|10000948|T_THE MOST Ensemble-Quintet 20|000024|90|Y|#102103|10001330|테스트_20181229|000000||Y|#102103|10001338|(테스트)T_카카오_대극장|000001|120|Y|#102103|10001327|T_(테스트)11번가_미술관|000009||Y|#102103|10001336";
      out.write(test1.getBytes("KSC5601"));
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
