package com.sasp.saspstore;

import android.content.Context;
import android.widget.Toast;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Product;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

public class FileClient extends Thread {
    private Socket socket;
    private Product product;
    private Context context;

    public FileClient(Product product, Context context) {
        Customer customer;
        this.product = product;
        this.context = context;
        if (!(DataManager.shared().getLoggedInAccount() instanceof Customer)) {
            return;
        }
        customer = (Customer) DataManager.shared().getLoggedInAccount();
        if (!customer.hasPurchasedProduct(product))
            return;
        FileServer server = product.getSellers().get(0).getFileServer();
        try {
            this.socket = new Socket(server.getInetAddress(), server.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream =
                    new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream outputStream =
                    new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputStream.writeUTF(product.getProductId());
            outputStream.flush();
            String res = inputStream.readUTF();
            if (res.equalsIgnoreCase("no-such-file")) {
                Toast.makeText(context, "The file cannot be found", Toast.LENGTH_SHORT);
                return;
            }
            File dir = new File(context.getFilesDir(), "mydir");
            if (!dir.exists()) {
                dir.mkdir();
            }
            try {
                File file = new File(dir, product.getName());
                FileWriter writer = new FileWriter(file);
                while (!((res = inputStream.readUTF()).equals("\0")))
                    writer.append(res);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
