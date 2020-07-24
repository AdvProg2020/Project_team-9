package com.sasp.saspstore;

import java.net.Socket;

public class FileClient extends Thread {
    private Socket socket;

    public FileClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
    }

    //    public String getFileUrl() {
//
//    }
}
