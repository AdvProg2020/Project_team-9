package com.sasp.saspstore;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BankAPI {
    public static final int PORT = 19083;
    //    public static final int PORT = 2222;
    public static final String IP = "http://2.tcp.ngrok.io/";
//    public static final String IP = "10.0.2.2";

    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;

    public static void tellBankAndReceiveResponse(String message, ResponseListener listener) {
        new Thread(() -> {
            try {
                Socket socket = new Socket(IP, PORT);
                outputStream = new DataOutputStream(socket.getOutputStream());
                inputStream = new DataInputStream(socket.getInputStream());
                new Thread(() -> {
                    try {
                        outputStream.writeUTF(message);
                        while (true) {
                            String response = inputStream.readUTF();
                            if (response.equals("")) continue;
                            listener.responseReceived(response);
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                System.out.println("Exception while initiating connection: " + e.getLocalizedMessage());
            }
        }).start();
    }
}

interface ResponseListener {
    void responseReceived(String response);
}