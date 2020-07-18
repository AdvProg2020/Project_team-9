package com.sasp.saspstore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Sale;
import com.sasp.saspstore.model.Seller;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SalesListActivity extends AppCompatActivity {

    ListView listView;
    EditText searchField;
    ArrayList<Sale> sales, originalSales;

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);

        listView = findViewById(R.id.allSalesList);
        searchField = findViewById(R.id.salesListSearchField);
        sales = new ArrayList<>();
        for (Sale sale : DataManager.shared().getAllSales()) {
            LocalDateTime now = LocalDateTime.now();
            if (!sale.getEndTime().isBefore(now)) sales.add(sale);
        }
        originalSales = new ArrayList<>(sales);
        ArrayAdapter<Sale> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sales);
        listView.setAdapter(adapter);
        Account account = DataManager.shared().getLoggedInAccount();
        if ((account instanceof Seller)) {
            // TODO: Why the var in the next line isn't used??
            Seller seller = (Seller) account;
            listView.setOnItemLongClickListener((parent, view, position, id) -> {
                Sale sale = (Sale) listView.getItemAtPosition(position);
                Intent intent = new Intent(this, AddSaleActivity.class);
                intent.putExtra("saleID", sale.getOffId());
                startActivity(intent);
                return true;
            });
        }
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Sale sale = (Sale) listView.getItemAtPosition(position);
            ArrayList<String> productIDs = findAppropriateProductIDs(sale);
            StringBuilder productIDsStringBuilder = getStringBuilderFromProductIDs(productIDs);
            navigateToProductsListActivityToShowSaleProducts(productIDsStringBuilder);
        });
        FloatingActionButton fab = findViewById(R.id.categories_fab);
        fab.setVisibility(account instanceof Seller ? View.VISIBLE : View.GONE);
        fab.setImageBitmap(textAsBitmap("+", 40, Color.WHITE));
        if (account instanceof Seller) fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddSaleActivity.class);
            startActivity(intent);
        });
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sales.clear();
                for (Sale sale : originalSales) {
                    if (charSequence == null || charSequence.equals("") || sale.toString().contains(charSequence))
                        sales.add(sale);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void navigateToProductsListActivityToShowSaleProducts(StringBuilder productIDsStringBuilder) {
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra("openOrSelect", "open");
        intent.putExtra("productIDs", productIDsStringBuilder.toString());
        startActivity(intent);
    }

    private StringBuilder getStringBuilderFromProductIDs(ArrayList<String> productIDs) {
        StringBuilder productIDsStringBuilder = new StringBuilder();
        for (int i = 0, productIDsSize = productIDs.size(); i < productIDsSize; i++) {
            String productID = productIDs.get(i);
            productIDsStringBuilder.append(productID);
            if (i != productIDsSize - 1) productIDsStringBuilder.append(";;;;");
        }
        return productIDsStringBuilder;
    }

    private ArrayList<String> findAppropriateProductIDs(Sale sale) {
        ArrayList<String> productIDs = new ArrayList<>();
        for (Product product : sale.getProducts()) {
            productIDs.add(product.getProductId());
        }
        return productIDs;
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