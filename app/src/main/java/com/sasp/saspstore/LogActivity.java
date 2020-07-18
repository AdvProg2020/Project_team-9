package com.sasp.saspstore;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Ad;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.DeliveryStatus;
import com.sasp.saspstore.model.Log;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.PurchaseLog;
import com.sasp.saspstore.model.SellLog;
import com.sasp.saspstore.model.Seller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class LogActivity extends AppCompatActivity {

    ListView listView;

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    // TODO: Ersale Kharid not tested

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        listView = findViewById(R.id.log_list);
        // TODO: A huge problem... shows all sales!!!
        boolean isSeller = (DataManager.shared().getLoggedInAccount() instanceof Seller);
        boolean isCustomer = (DataManager.shared().getLoggedInAccount() instanceof Customer);
        boolean isAdministrator = (DataManager.shared().getLoggedInAccount() instanceof Administrator);
        ArrayList<Log> logs = getAppropriateLogs(isSeller, isCustomer, isAdministrator);
        ArrayAdapter<Log> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, logs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Log log = (Log) listView.getItemAtPosition(position);
            StringBuilder message = new StringBuilder("تاریخ: " + log.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    "\n" +
                    "مبلغ: " + log.getPrice() +
                    "\n" +
                    "میزان تخفیف: " + log.getDiscountAmount() +
                    "\n" +
                    "لیست کالاها:" +
                    "\n");
            if (log instanceof PurchaseLog) {
                PurchaseLog purchaseLog = (PurchaseLog) log;
                message.append("نام خریدار: ").append(purchaseLog.getCustomer().getFirstName())
                        .append(" ").append(purchaseLog.getCustomer().getLastName()).append("\n")
                        .append("آدرس خریدار: ").append(purchaseLog.getCustomer().getAddress())
                        .append("\n").append("وضعیت سفارش: ").append(purchaseLog.getDeliveryStatus());
            }
            for (Product product : log.getProducts().keySet())
                message.append(product.getName()).append("\n");
            AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);
            builder.setTitle("اطلاعات تراکنش");
            builder.setMessage(message);
            builder.setNeutralButton("بازگشت", null);
            if (isAdministrator && (log instanceof PurchaseLog) && log.getDeliveryStatus() == DeliveryStatus.WAITING) {
                builder.setPositiveButton("تغییر وضعیت درخواست به ارسال شده", (dialogInterface, i) -> {
                    log.setDeliveryStatus(DeliveryStatus.SENT);
                    DataManager.shared().syncPurchaseLogs();
                });
            }
            builder.show();
        });
    }

    private ArrayList<Log> getAppropriateLogs(boolean isSeller, boolean isCustomer, boolean isAdministrator) {
        ArrayList<Log> logs = new ArrayList<>();
        if (isSeller) {
            for (Log log : DataManager.shared().getAllLogs()) {
                if (log instanceof SellLog && ((SellLog) log).getSeller().getUsername()
                        .equals(DataManager.shared().getLoggedInAccount().getUsername())) {
                    logs.add(log);
                }
            }
        } else if (isCustomer) {
            for (Log log : DataManager.shared().getAllLogs()) {
                if (log instanceof PurchaseLog && ((PurchaseLog) log).getCustomer().getUsername()
                        .equals(DataManager.shared().getLoggedInAccount().getUsername())) {
                    logs.add(log);
                }
            }
        } else if (isAdministrator) {
            for (Log log : DataManager.shared().getAllLogs()) {
                if (log instanceof PurchaseLog) {
                    logs.add(log);
                }
            }
        }
        return logs;
    }
}