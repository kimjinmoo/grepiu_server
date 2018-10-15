package com.grepiu.test.application.socket.test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cancel extends Process {

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
                // 문자 입력
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("몰라요");
                //응답 문자 처리
                ois = new ObjectInputStream(socket.getInputStream());
                String message = (String) ois.readObject();
                System.out.println("응답값 : " + message);
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
