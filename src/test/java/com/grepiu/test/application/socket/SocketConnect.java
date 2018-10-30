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

        String response = SocketUtils.sendDataStream(new Socket(), ip, port, header.getDate());
        System.out.println("response : [" + response + "]end");

    }
}
