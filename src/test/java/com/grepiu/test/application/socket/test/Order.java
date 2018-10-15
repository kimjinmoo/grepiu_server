package com.grepiu.test.application.socket.test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Order extends Process {
    @Override
    public String execute() {
        System.out.println("주문 취소를 한다");
        try {
            Socket socket = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;

            while(true){
                // 소켓 초기화
                socket = init();
                // 문자열 입력
                oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("메세지 전달");
                oos.writeObject("김진무||파워구매원");
                // 응답값
                ois = new ObjectInputStream(socket.getInputStream());
                String message = (String) ois.readObject();
                System.out.println("응답값: " + message);
                // 종료
                ois.close();
                oos.close();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
