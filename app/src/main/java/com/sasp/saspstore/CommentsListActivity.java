package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Comment;
import com.sasp.saspstore.model.Log;
import com.sasp.saspstore.model.Product;

import java.util.ArrayList;

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
    }
}