package com.sasp.saspstore;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Log;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.PurchaseLog;
import com.sasp.saspstore.model.SellLog;
import com.sasp.saspstore.model.Seller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LogActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        listView = (ListView) findViewById(R.id.log_list);
        // TODO: A huge problem... shows all sales!!!
        boolean isSeller = (DataManager.shared().getLoggedInAccount() instanceof Seller);
        boolean isCustomer = (DataManager.shared().getLoggedInAccount() instanceof Customer);
        ArrayList<Log> logs = getAppropriateLogs(isSeller, isCustomer);
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
            for (Product product : log.getProducts().keySet())
                message.append(product.getName()).append("\n");
            new AlertDialog.Builder(getApplicationContext())
                    .setTitle("اطلاعات تراکنش")
                    .setMessage(message)
                    .setPositiveButton("بازگشت", null)
                    .show();
        });
    }

    private ArrayList<Log> getAppropriateLogs(boolean isSeller, boolean isCustomer) {
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
        }
        return logs;
    }
}