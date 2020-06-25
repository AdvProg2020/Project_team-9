package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Log;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.PurchaseLog;
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
        ArrayList<Log> logs = new ArrayList<>(DataManager.shared().getAllLogs());
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
                    .setPositiveButton(android.R.string.cancel, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }
}