package com.sasp.saspstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.controller.ProductsAdapter;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Seller;
import com.sasp.saspstore.ui.home.EditProfileActivity;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    ArrayList<Product> selectedProducts = new ArrayList<>();
    ProductsAdapter productsAdapter;

    boolean shouldOpen = true;
    String categoryID;

    // TODO: Edit and Delete Product (needed long press) not done yet. Better to use create product activity to achieve the tasks.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Intent intent = getIntent();
        String productIDs = intent.getStringExtra("productIDs");
        String openOrSelect = intent.getStringExtra("openOrSelect");
        categoryID = intent.getStringExtra("categoryID");
        if (categoryID == null) categoryID = "";
        shouldOpen = openOrSelect == null || openOrSelect.equals("open");
        ArrayList<Product> products;
        if (productIDs != null && !productIDs.equals("")) {
            products = new ArrayList<>();
            String[] parts = productIDs.split(";;;;");
            for (String productID : parts)
                products.add(DataManager.shared().getProductWithId(productID));
        } else products = DataManager.shared().getAllProducts();

        GridView gridView = findViewById(R.id.productsListGridView);
        // TODO: Product images here...?
        // TODO: Does the second line work in converting array???
        Product[] productsArray = products.toArray(new Product[0]);
        productsAdapter = new ProductsAdapter(this, productsArray);
        gridView.setAdapter(productsAdapter);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Product product = productsArray[position];
            if (shouldOpen) {
                Intent productDetails = new Intent(ProductListActivity.this, EachProductActivity.class);
                productDetails.putExtra("productID", product.getProductId());
                startActivity(productDetails);
            } else {
                if (product.isSelected()) {
                    product.setSelected(false);
                    selectedProducts.remove(product);
                } else {
                    product.setSelected(true);
                    selectedProducts.add(product);
                }
                productsAdapter.notifyDataSetChanged();
            }
        });
        Account account = DataManager.shared().getLoggedInAccount();
        if (account instanceof Seller) gridView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Product product = productsArray[i];
            new AlertDialog.Builder(ProductListActivity.this).setTitle("ویرایش و یا حذف محصول")
                    .setMessage("نام محصول: " + product.getName())
                    .setPositiveButton("ویرایش محصول", (dialogInterface, i1) -> {
                        Intent editIntent = new Intent(ProductListActivity.this, AddProductActivity.class);
                        editIntent.putExtra("categoryID", categoryID);
                        editIntent.putExtra("productID", product.getProductId());
                        startActivityForResult(editIntent, 1);
                    })
                .setNegativeButton("حذف محصول", (dialogInterface, i1) -> {
                    DataManager.shared().removeProduct(product.getProductId());
                    productsAdapter.notifyDataSetChanged();
                }).setNeutralButton("بازگشت", null).show();
            return true;
        });
        checkFabStatus();
    }

    private void checkFabStatus() {
        FloatingActionButton submitFab = findViewById(R.id.productsList_fab);
        FloatingActionButton addFab = findViewById(R.id.productsList_addProduct_fab);
        if (!shouldOpen) {
            addFab.setVisibility(View.GONE);
            submitFab.setVisibility(View.VISIBLE);
            submitFab.setImageBitmap(textAsBitmap("✓", 40, Color.WHITE));
            submitFab.setOnClickListener(view -> {
                Intent backIntent = new Intent();
                StringBuilder selectedIDs = new StringBuilder();
                for (int i = 0, selectedProductsSize = selectedProducts.size(); i < selectedProductsSize; i++) {
                    Product product = selectedProducts.get(i);
                    selectedIDs.append(product.getProductId());
                    if (i != selectedProductsSize - 1) selectedIDs.append(";;;;");
                }
                backIntent.putExtra("selectedIDs", selectedIDs.toString());
                for (Product product : selectedProducts) {
                    product.setSelected(false);
                }
                DataManager.saveData();
                setResult(RESULT_OK, backIntent);
                finish();
            });
        }
        else {
            submitFab.setVisibility(View.GONE);
            if (categoryID != null && !categoryID.equals("")) {
                addFab.setVisibility(View.VISIBLE);
                addFab.setImageBitmap(textAsBitmap("+", 40, Color.WHITE));
                addFab.setOnClickListener(view -> {
                    Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
                    intent.putExtra("categoryID", categoryID);
                    startActivityForResult(intent, 1);
                });
            } else addFab.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) productsAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.getproducts_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.getproductsmenu_btnSubmit) {
            Intent intent = new Intent();
            StringBuilder selectedIDs = new StringBuilder();
            for (int i = 0, selectedProductsSize = selectedProducts.size(); i < selectedProductsSize; i++) {
                Product product = selectedProducts.get(i);
                selectedIDs.append(product.getProductId());
                if (i != selectedProductsSize - 1) selectedIDs.append(";;;;");
            }
            intent.putExtra("selectedIDs", selectedIDs.toString());
            for (Product product : selectedProducts) {
                product.setSelected(false);
            }
            DataManager.saveData();
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (Product product : selectedProducts) {
            product.setSelected(false);
        }
        DataManager.saveData();
    }
}