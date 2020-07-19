package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Ad;
import com.sasp.saspstore.model.Auction;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Message;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionChatActivity extends AppCompatActivity {

    ListView listView;
    EditText chatTxt;

    Auction auction;

    Timer timer = new Timer();

    ArrayAdapter<Message> adapter;

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_chat);

        chatTxt = findViewById(R.id.auction_chatTxt);

        listView = findViewById(R.id.auctionChat_list);
        Intent intent = getIntent();
        auction = DataManager.shared().getAuctionWithId(intent.getStringExtra("auctionID"));
        if (auction == null) finish();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, auction.getMessages());
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                reloadMessages();
            }
        }, 1000, 1000);
    }

    private void reloadMessages() {
        Gonnect.getData(DataManager.IP_SERVER + "?action=getMessagesForAuction", (isSuccess, errorOrResponse) -> {
            runOnUiThread(() -> {
                if (isSuccess) {
                    ArrayList<Message> allMessagesFromServer = new Gson().fromJson(errorOrResponse, new TypeToken<ArrayList<Message>>() {
                    }.getType());
                    for (Message message : allMessagesFromServer) {
                        if (!auction.doesContainMessageWithID(message.getId())) auction.addMessage(message);
                    }
                    DataManager.shared().syncAuctions();
                    adapter.notifyDataSetChanged();
                }
            });
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    public void sendMessageTapped(View view) {
        auction.addMessage(new Message(DataManager.getNewId(),
                DataManager.shared().getLoggedInAccount().getUsername(), chatTxt.getText().toString()));
        Toast.makeText(this, "پیام شما ارسال شد", Toast.LENGTH_LONG).show();
        chatTxt.setText("");
        reloadMessages();
    }
}