package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Coupon;
import com.sasp.saspstore.model.Sale;
import com.sasp.saspstore.model.Seller;

import java.util.logging.SocketHandler;

public class SalesListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);

        listView = findViewById(R.id.allSalesList);
        ArrayAdapter<Sale> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, DataManager.shared().getAllSales());
        listView.setAdapter(adapter);
        Account account = DataManager.shared().getLoggedInAccount();
        if ((account instanceof Seller)) {
            // TODO: Why the var in the next line isn't used??
            Seller seller = (Seller) account;
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Sale sale = (Sale) listView.getItemAtPosition(position);
                Intent intent = new Intent(this, AddSaleActivity.class);
                intent.putExtra("saleID", sale.getOffId());
                startActivity(intent);
            });
        }
        FloatingActionButton fab = findViewById(R.id.categories_fab);
        fab.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        fab.setImageBitmap(textAsBitmap("+", 40, Color.WHITE));
        if (account instanceof Seller) fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddSaleActivity.class);
            startActivity(intent);
        });
    }

    private Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}