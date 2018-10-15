package com.grepiu.test.application.socket.test;

import java.net.Socket;

public abstract class Process {
    private String ip = "127.0.0.1";
        private int port = 9090;

    public abstract String execute();

    protected Socket init() throws Exception {
        // todo connect init
        System.out.println("ip : " +  ip);
        System.out.println("port : " +  port);

        return new Socket(ip, port);
    }
}
