package com.sasp.saspstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Assistant;
import com.sasp.saspstore.model.AssistantMessage;
import com.sasp.saspstore.model.Auction;
import com.sasp.saspstore.model.Coupon;
import com.sasp.saspstore.model.Message;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class AssistantChatActivity extends AppCompatActivity {

    ListView listView;
    EditText chatTxt;
    Button chatButton;

    String assistantUsername;

    Timer timer = new Timer();

    ArrayAdapter<AssistantMessage> adapter;
    ArrayList<AssistantMessage> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_chat);

        chatTxt = findViewById(R.id.assistant_chatTxt);
        chatButton = findViewById(R.id.assistant_chatButton);

        listView = findViewById(R.id.assistantChat_list);
        Intent intent = getIntent();
        assistantUsername = intent.getStringExtra("assistantUsername");
        populateMessagesArray();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, messages);
        listView.setAdapter(adapter);
        if (assistantUsername == null) {
            // Has come from an assistant's page; show all contacts instead
            chatButton.setVisibility(View.GONE);
            chatTxt.setVisibility(View.GONE);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                AssistantMessage message = (AssistantMessage) listView.getItemAtPosition(position);
                if (message.getReceiver().getUsername().equals(DataManager.shared().getLoggedInAccount().getUsername())) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.coupon_editing_dialog, null);
                    dialogBuilder.setView(dialogView);
                    final TextView firstTextView = dialogView.findViewById(R.id.couponAlertTextView);
                    final TextView secondTextView = dialogView.findViewById(R.id.couponAlertProductsTextView);
                    StringBuilder first = new StringBuilder("ارسال پیام به ").append(message.getSender().getUsername());
                    firstTextView.setText(first);
                    secondTextView.setText("");
                    secondTextView.setVisibility(View.GONE);
                    final EditText editDiscountPercent = dialogView.findViewById(R.id.editDiscountPercent);
                    final EditText editMaximumDiscount = dialogView.findViewById(R.id.editMaximumDiscount);
                    editDiscountPercent.setText("");
                    editMaximumDiscount.setVisibility(View.GONE);
                    dialogBuilder.setTitle("ارسال پیام").setMessage("").setPositiveButton("ارسال", (dialog, whichButton) -> {
                        DataManager.shared().getAllAssistantMessages().add(new AssistantMessage(DataManager.getNewId(),
                                DataManager.shared().getLoggedInAccount().getUsername(), message.getSender().getUsername(),
                                editDiscountPercent.getText().toString()));
                        Toast.makeText(this, "پیام شما ارسال شد", Toast.LENGTH_LONG).show();
                        reloadMessages();
                    }).setNeutralButton("بازگشت", null);
                    AlertDialog b = dialogBuilder.create();
                    b.show();
                }
            });
        } else {
            // Entered from a customer's page, contacting a specific assistant
            chatButton.setVisibility(View.VISIBLE);
            chatTxt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    public void populateMessagesArray() {
        messages.clear();
        Account currentAccount = DataManager.shared().getLoggedInAccount();
        if (assistantUsername == null) {
            for (AssistantMessage assistantMessage : DataManager.shared().getAllAssistantMessages()) {
                if (assistantMessage.getSender().getUsername().equals(currentAccount.getUsername()) ||
                        assistantMessage.getReceiver().getUsername().equals(currentAccount.getUsername()))
                    messages.add(assistantMessage);
            }
        } else {
            for (AssistantMessage assistantMessage : DataManager.shared().getAllAssistantMessages()) {
                if ((assistantMessage.getSender().getUsername().equals(currentAccount.getUsername()) &&
                        assistantMessage.getReceiver().getUsername().equals(assistantUsername)) ||
                        (assistantMessage.getReceiver().getUsername().equals(currentAccount.getUsername()) &&
                                assistantMessage.getSender().getUsername().equals(assistantUsername)))
                    messages.add(assistantMessage);
            }
        }
    }

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
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

    private boolean doesMessagesArrayContainMessageWithID(String id) {
        for (AssistantMessage message : DataManager.shared().getAllAssistantMessages()) {
            if (message.getId().equals(id)) return true;
        }
        return false;
    }

    private void reloadMessages() {
        Gonnect.getData(DataManager.IP_SERVER + "?action=getAssistantMessages", (isSuccess, errorOrResponse) -> runOnUiThread(() -> {
            if (isSuccess) {
                ArrayList<AssistantMessage> allMessagesFromServer = new Gson().fromJson(errorOrResponse,
                        new TypeToken<ArrayList<AssistantMessage>>() {
                        }.getType());
                for (AssistantMessage message : allMessagesFromServer) {
                    if (!doesMessagesArrayContainMessageWithID(message.getId()))
                        DataManager.shared().getAllAssistantMessages().add(message);
                }
                populateMessagesArray();
                DataManager.shared().syncAssistantMessages();
                adapter.notifyDataSetChanged();
            }
        }));
    }

    public void sendMessageTapped(View view) {
        // Only customer can enter here, and the sender is himself and the receiver is the assistant
        DataManager.shared().getAllAssistantMessages().add(new AssistantMessage(DataManager.getNewId(),
                DataManager.shared().getLoggedInAccount().getUsername(), assistantUsername, chatTxt.getText().toString()));
        Toast.makeText(this, "پیام شما ارسال شد", Toast.LENGTH_LONG).show();
        chatTxt.setText("");
        reloadMessages();
    }
}

// TODO: Assistant is not tested at all!!