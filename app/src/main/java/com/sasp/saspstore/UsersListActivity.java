package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Coupon;

public class UsersListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        listView = (ListView) findViewById(R.id.allCouponsList);
        // TODO: Should sometimes show only customers... and not sellers and...!!!
        ArrayAdapter<Account> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, DataManager.shared().getAllAccounts());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Account account = (Account) listView.getItemAtPosition(position);
            Intent intent = new Intent();
            intent.putExtra("selectedUsername", account.getUsername());
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}