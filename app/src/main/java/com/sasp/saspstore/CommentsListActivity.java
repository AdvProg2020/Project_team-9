package com.sasp.saspstore;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Comment;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Seller;

public class CommentsListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_list);

        listView = findViewById(R.id.comments_list);
        Intent intent = getIntent();
        String productID = intent.getStringExtra("productID");
        if (productID == null || productID.equals("")) return;
        Product product = DataManager.shared().getProductWithId(productID);
        ArrayAdapter<Comment> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, product.getComments());
        listView.setAdapter(adapter);
        Account account = DataManager.shared().getLoggedInAccount();
        if (account instanceof Seller) listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Comment comment = (Comment) listView.getItemAtPosition(i);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.coupon_editing_dialog, null);
            dialogBuilder.setView(dialogView);
            final TextView firstTextView = (TextView) dialogView.findViewById(R.id.couponAlertTextView);
            final TextView secondTextView = (TextView) dialogView.findViewById(R.id.couponAlertProductsTextView);
            firstTextView.setText("لطفا پاسخ خود را وارد نمایید");
            secondTextView.setText("");
            final EditText firstEditText = (EditText) dialogView.findViewById(R.id.editDiscountPercent);
            final EditText secondEditText = (EditText) dialogView.findViewById(R.id.editMaximumDiscount);
            secondEditText.setVisibility(View.GONE);
            dialogBuilder.setTitle("پاسخ به نظر").setMessage("").setPositiveButton("ثبت پاسخ", (dialog, whichButton) -> {
                String response = firstEditText.getText().toString();
                comment.setResponse(response);
                DataManager.saveData();
            }).setNeutralButton("بازگشت", null);
            AlertDialog b = dialogBuilder.create();
            b.show();
        });
    }
}