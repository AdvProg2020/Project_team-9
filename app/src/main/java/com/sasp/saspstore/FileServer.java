package com.sasp.saspstore;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Seller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
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

    public InetAddress getInetAddress() {
        return getServerSocket().getInetAddress();
    }

    public int getPort() {
        return getServerSocket().getLocalPort();
    }

    private class ServerImpl extends Thread {
        private ServerSocket serverSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ServerImpl(int port) throws IOException {
            this.serverSocket = new ServerSocket(port);
        }

        public ServerSocket getServerSocket() {
            return serverSocket;
        }

        @Override
        public void run() {
            Socket clientSocket;
            try {
                // Open a server socket listening on port 8080
                InetAddress addr = InetAddress.getByName(getLocalIpAddress());
                serverSocket = new ServerSocket(8080, 0, addr);
                clientSocket = serverSocket.accept();

                // Client established connection.
                // Create input and output streams
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                while (mustContinue) {
                    clientSocket = serverSocket.accept();
                    DataInputStream inputStream =
                            new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                    DataOutputStream outputStream =
                            new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                    new ClientHandler(clientSocket, inputStream, outputStream).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String getLocalIpAddress() throws Exception {
            String resultIpv6 = "";
            String resultIpv4 = "";

            for (Enumeration en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {

                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses();
                     enumIpAddr.hasMoreElements();) {

                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if(!inetAddress.isLoopbackAddress()){
                        if (inetAddress instanceof Inet4Address) {
                            resultIpv4 = inetAddress.getHostAddress().toString();
                        } else if (inetAddress instanceof Inet6Address) {
                            resultIpv6 = inetAddress.getHostAddress().toString();
                        }
                    }
                }
            }
            return ((resultIpv4.length() > 0) ? resultIpv4 : resultIpv6);
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
                        File file = new File(product.getFilePath());
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            writeToOutputStream(scanner.nextLine());
                        }
                        writeToOutputStream("\0");
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
