package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Auction;
import com.sasp.saspstore.model.Comment;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.DeliveryStatus;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.PurchaseLog;
import com.sasp.saspstore.model.SellLog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllAuctionsActivity extends AppCompatActivity {

    ListView listView;
    String response;

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_auctions);

        listView = findViewById(R.id.auctions_list);
        Intent intent = getIntent();
        response = intent.getStringExtra("response");
        ArrayList<Auction> auctions = new Gson().fromJson(response, new TypeToken<ArrayList<Auction>>() {
        }.getType());
        DataManager.shared().setAuctions(auctions);
        ArrayAdapter<Auction> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, DataManager.shared().getAuctions());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Auction auction = (Auction) listView.getItemAtPosition(i);
            if (auction.getEndTime().isBefore(LocalDateTime.now())) {
                Customer customer = (Customer) DataManager.shared().getLoggedInAccount();
                Customer auctionLastCustomer = auction.getLastCustomer();
                if (auctionLastCustomer != null && customer.getUsername().equals(auctionLastCustomer.getUsername()))
                    showAlertForWinner(auction, customer);
            } else showAlertForCustomersWhileInAuction(auction);
        });

    }

    private void showAlertForWinner(Auction auction, Customer customer) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AllAuctionsActivity.this)
                .setTitle("تبریک! شما برنده مزایده شدید");
//        if (customer.getCredit() - auction.getProduct().getRoundedPriceAfterDiscount() < DataManager.shared().getMimimumCredit()) {
//            alertDialog.setMessage("متاسفانه اعتبار شما برای دریافت محصول خود کافی نیست!");
//            alertDialog.setNeutralButton("بازگشت", null);
//        } else {
        alertDialog.setMessage("با کلیک روی دکمه دریافت، کالای برنده شده را از آن خود کنید!");
        alertDialog.setPositiveButton("دریافت کالا", (dialogInterface, i1) -> showAlertForCustomersWhileWinnerAndHasCredit(auction, customer));
//        }
        alertDialog.show();
    }

    // TODO: Nothing tested here!!

    private void showAlertForCustomersWhileWinnerAndHasCredit(Auction auction, Customer customer) {
        auction.getProduct().decrementNumberAvailable();
        auction.getProduct().getCurrentSeller().increaseCredit((int) (auction.getPriceUpToNow() *
                ((double) (100 - DataManager.shared().getKarmozd())) / 100));
//        customer.decreaseCredit(auction.getProduct().getRoundedPriceAfterDiscount());
        HashMap<Product, Integer> hashMap = new HashMap<>();
        hashMap.put(auction.getProduct(), 1);
        PurchaseLog purchaseLog = new PurchaseLog(DataManager.getNewId(), LocalDateTime.now(), auction.getPriceUpToNow(),
                0, hashMap, DeliveryStatus.WAITING, (Customer) DataManager.shared().getLoggedInAccount());
        DataManager.shared().addLog(purchaseLog);
        DataManager.shared().syncCustomers();
        DataManager.shared().syncProducts();
    }

    private void showAlertForCustomersWhileInAuction(Auction auction) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.coupon_editing_dialog, null);
        dialogBuilder.setView(dialogView);
        final TextView firstTextView = dialogView.findViewById(R.id.couponAlertTextView);
        final TextView secondTextView = dialogView.findViewById(R.id.couponAlertProductsTextView);
        firstTextView.setText("کالای مورد مزایده: " + auction.getProduct() + "\n" +
                "مبلغ پیشنهادی تاکنون " + auction.getPriceUpToNow() + " است. مبلغ پیشنهادی شما چیست؟ مبلغ شما تنها در صورتی ثبت می‌شود که بیش از مبلغ پیشنهادی تاکنون بوده و در کیف پول شما نیز موجود باشد.");
        secondTextView.setText("");
        final EditText firstEditText = dialogView.findViewById(R.id.editDiscountPercent);
        final EditText secondEditText = dialogView.findViewById(R.id.editMaximumDiscount);
        secondEditText.setVisibility(View.GONE);
        // TODO: Invalid input for price?
        dialogBuilder.setTitle("مزایده").setMessage("").setPositiveButton("ثبت مبلغ پیشنهادی", (dialog, whichButton) -> {
            int newPrice = Integer.parseInt(firstEditText.getText().toString());
            Customer customer = (Customer) DataManager.shared().getLoggedInAccount();
            if (newPrice > auction.getPriceUpToNow() && newPrice <= customer.getCredit() - DataManager.shared().getMimimumCredit()) {
                customer.decreaseCredit(newPrice);
                auction.setLastCustomer(customer);
                auction.setPriceUpToNow(newPrice);
                DataManager.shared().syncAuctions();
            }
        }).setNegativeButton("بازگشت", null)
                .setNeutralButton("ورود به صفحه چت", (dialog, whichButton) -> {
                    Intent chatIntent = new Intent(AllAuctionsActivity.this, AuctionChatActivity.class);
                    chatIntent.putExtra("auctionID", auction.getId());
                    // TODO: Not only not checked auction at all, but also multiple people chatting at once and the timer fires...!
                    startActivity(chatIntent);
                });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}