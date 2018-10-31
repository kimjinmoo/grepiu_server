package com.grepiu.test.application.socket;

import com.grepiu.test.application.socket.test.HeaderVO;

import com.grepiu.www.process.common.utils.SocketUtils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import org.assertj.core.util.Lists;

public class SocketConnect {

    private static final String ip = "";
    private static final int port = 9090;
    private static final int buffer = 100;

    public static void main(String...args) throws Exception {
        HeaderVO header = new HeaderVO();
        header.setCommunicationType("11");
        header.setTextLength("3123");
        header.setTextNo("1323");
        header.setData(UUID.randomUUID().toString());

//        String response = SocketUtils.sendDataStream(new Socket(), ip, port, header.getDate());
        StringBuilder sb = new StringBuilder();
        String sample = sb.append("SAC!@#")
            .append("   133")
            .append("9102")
            .append("v9.9.9.9    ")
            .append("102103                        ")
            .append("103065                        ")
            .append("N")
            .append("                             ")
            .append("    ")
            .append(" ")
            .append("000000")
            .append("20181029")
            .append("20181030").toString();
        System.out.println("sample size : [" + sample.length() + "]");
        String response = SocketUtils.sendDataStream(new Socket(), ip, port, sample.getBytes("KSC5601"));

        StringBuilder sb2 = new StringBuilder();
        // N 항목 처리
        String sample2 = sb2.append("SAC!@#")
            .append("000133") // 왼쪽 공란 0 채움
            .append("9102")
            .append("v9.9.9.9    ")
            .append("102103                        ")
            .append("103065                        ")
            .append("N")
            .append("                             ")
            .append("    ")
            .append(" ")
            .append("000000")
            .append("20181029")
            .append("20181030").toString();
        System.out.println("sample size : [" + sample2.length() + "]");
        String response2 = SocketUtils.sendDataStream(new Socket(), ip, port, sample2.getBytes("KSC5601"));
//        System.out.println("response : [" + response + "]end");

    }
}
