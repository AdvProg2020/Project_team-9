package com.sasp.saspstore;

import com.google.gson.Gson;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Seller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileServer {
    Seller seller;
    private ServerImpl server;
    private boolean mustContinue;

    public FileServer(int port, Seller seller) throws IOException {
        server = new ServerImpl(port);
        mustContinue = true;
        this.seller = seller;
    }

    public void start() {
        server.start();
    }

    private <T> void writeToConsole(T message) {
        System.out.println(message);
    }

    private Matcher getMatcher(String regex, String query) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(query);
    }

    public ServerSocket getServerSocket() {
        return server.getServerSocket();
    }

    public void end() {
        mustContinue = false;
    }

    private class ServerImpl extends Thread {
        private ServerSocket serverSocket;

        public ServerImpl(int port) throws IOException {
            this.serverSocket = new ServerSocket(port);
        }

        public ServerSocket getServerSocket() {
            return serverSocket;
        }

        @Override
        public void run() {
            Socket clientSocket;
            while (mustContinue) {
                try {
                    clientSocket = serverSocket.accept();
                    DataInputStream inputStream =
                            new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                    DataOutputStream outputStream =
                            new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                    new ClientHandler(clientSocket, inputStream, outputStream).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler extends Thread {
        Socket clientSocket;
        DataInputStream inputStream;
        DataOutputStream outputStream;

        public ClientHandler(Socket clientSocket,
                             DataInputStream inputStream, DataOutputStream outputStream) {
            this.clientSocket = clientSocket;
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }

        private void handleClient() {
            String productId, result;
            Product product;
            boolean exit = false;
            while (!exit) {
                try {
                    result = "";
                    productId = inputStream.readUTF();
                    product = DataManager.shared().getProductWithId(productId);
                    if (product == null) {
                        writeToOutputStream("no-such-file");
                    } else {
                        File file = new File(product.getFileUri().getPath());
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            writeToOutputStream(scanner.nextLine());
                        }
                    }
                    exit = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void writeToOutputStream(String message) {
            try {
                outputStream.writeUTF(message);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            handleClient();
        }
    }
}
