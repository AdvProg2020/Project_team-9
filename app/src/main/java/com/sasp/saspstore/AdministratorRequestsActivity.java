package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Category;
import com.sasp.saspstore.model.Request;

public class AdministratorRequestsActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_requests);

        listView = findViewById(R.id.allRequestsList);
        ArrayAdapter<Request> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,
                DataManager.shared().getAllRequests());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                Request request = (Request) listView.getItemAtPosition(position);
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        request.fulfill();
                        DataManager.shared().removeRequest(request);
                        // TODO: First argument of the next Snackbar??? and its coloring and stuff like this??
                        Snackbar.make(listView, "درخواست با موفقیت در حالت تایید قرار گرفت", Snackbar.LENGTH_LONG).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        DataManager.shared().removeRequest(request);
                        Snackbar.make(listView, "درخواست با موفقیت در حالت رد قرار گرفت", Snackbar.LENGTH_LONG).show();
                        break;
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("تعیین تکلیف درخواست").setPositiveButton("قبول درخواست", dialogClickListener)
                    .setNegativeButton("رد درخواست", dialogClickListener).setNeutralButton("بازگشت", null).show();
        });
    }
}