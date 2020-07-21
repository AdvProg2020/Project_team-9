package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Assistant;
import com.sasp.saspstore.model.Auction;
import com.sasp.saspstore.model.Coupon;

import java.util.ArrayList;

public class OnlineAssistantsListActivity extends AppCompatActivity {

    ArrayList<Assistant> onlineAssistants = new ArrayList<>();
    ArrayAdapter<Assistant> adapter;
    String response;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_assistants_list);

        Intent intent = getIntent();
        response = intent.getStringExtra("response");
        ArrayList<String> strings = new Gson().fromJson(response, new TypeToken<ArrayList<String>>() {
        }.getType());
        onlineAssistants = new ArrayList<>();
        for (String string : strings) {
            onlineAssistants.add((Assistant) DataManager.shared().getAccountWithGivenUsername(string));
        }

        listView = findViewById(R.id.onlineAssistantsList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, onlineAssistants);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Assistant assistant = (Assistant) listView.getItemAtPosition(position);
            Intent showIntent = new Intent(this, AssistantChatActivity.class);
            showIntent.putExtra("assistantUsername", assistant.getUsername());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}