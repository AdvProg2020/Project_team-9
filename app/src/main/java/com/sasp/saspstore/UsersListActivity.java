package com.sasp.saspstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;

public class UsersListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Intent receivedIntent = getIntent();
        // TODO: Error if no sender...?
        String sender = receivedIntent.getStringExtra("sender");
        listView = findViewById(R.id.allUsersList);
        // TODO: Should sometimes show only customers... and not sellers and...!!!
        ArrayAdapter<Account> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,
                DataManager.shared().getAllAccounts());
        listView.setAdapter(adapter);
        if (sender != null && sender.equals("administratorProfile")) {
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Account account = (Account) listView.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            DataManager.shared().removeAccount(account);
                            adapter.notifyDataSetChanged();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                };
                builder.setMessage("آیا مطمینید که می‌خواهید \"" + account.getUsername() + "\" را حذف کنید؟")
                        .setPositiveButton("بله", dialogClickListener).setNegativeButton("خیر", dialogClickListener);
                if (!account.getUsername().equals(DataManager.shared().getLoggedInAccount().getUsername()))
                    builder.show();
            });
        } else {
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Account account = (Account) listView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("selectedUsername", account.getUsername());
                setResult(RESULT_OK, intent);
                finish();
            });
        }
    }
}