package com.sasp.saspstore;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Seller;

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
        Seller seller = product.getSellers().get(0);
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket("2.tcp.ngrok.io", 15009);
            DataInputStream inputStream =
                    new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream outputStream =
                    new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputStream.writeUTF(product.getProductId());
            outputStream.flush();
            String res = inputStream.readUTF();
            if (res.equalsIgnoreCase("no-such-file")) {
                Toast.makeText(context, "The file cannot be found", Toast.LENGTH_SHORT).show();
                return;
            }
            File dir = new File(context.getFilesDir(), "mydir");
            if (!dir.exists()) {
                dir.mkdir();
            }
            try {
                Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
                File file = new File(dir, product.getName());
                FileWriter writer = new FileWriter(file);
                while (!((res = inputStream.readUTF()).equals("\0")))
                    writer.append(res);
                writer.flush();
                writer.close();
                Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
