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
import com.sasp.saspstore.model.Coupon;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Seller;
import com.sasp.saspstore.ui.home.EditProfileActivity;
import com.sasp.saspstore.view.menus.FilterProductsActivity;

import java.util.ArrayList;
import java.util.Collection;

public class ProductListActivity extends AppCompatActivity {

    ArrayList<Product> selectedProducts = new ArrayList<>();
    ProductsAdapter productsAdapter;

    boolean shouldOpen = true;
    String categoryID;

    ArrayList<Product> originalProducts;
    ArrayList<Product> products;

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
        if (productIDs != null && !productIDs.equals("")) {
            products = new ArrayList<>();
            originalProducts = new ArrayList<>();
            String[] parts = productIDs.split(";;;;");
            for (String productID : parts) {
                products.add(DataManager.shared().getProductWithId(productID));
                originalProducts.add(DataManager.shared().getProductWithId(productID));
            }
        } else {
            products = DataManager.shared().getAllProducts();
            originalProducts = DataManager.shared().getAllProducts();
        }

        GridView gridView = findViewById(R.id.productsListGridView);
        // TODO: Product images here...?
        // TODO: Does the second line work in converting array???
        productsAdapter = new ProductsAdapter(this, products);
        gridView.setAdapter(productsAdapter);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Product product = products.get(position);
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
        if (account instanceof Seller)
            gridView.setOnItemLongClickListener((adapterView, view, i, l) -> {
                Product product = (Product) gridView.getItemAtPosition(i);
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
        } else {
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
        if (requestCode == 2 && resultCode == RESULT_OK) {
            applyFilters(data);
        }
    }

    private void applyFilters(Intent intent) {
        productNameFilter = intent.getStringExtra("productName");
        productDescriptionFilter = intent.getStringExtra("productDescription");
        brandFilter = intent.getStringExtra("brand");
        priceFilter = intent.getStringExtra("price");
        sellerNameOrCompanyFilter = intent.getStringExtra("sellerNameOrCompany");
        onlyAvailableProductsFilter = intent.getStringExtra("onlyAvailableProducts");

        products.clear();
        for (Product originalProduct : originalProducts) {
            boolean shouldBeAdded = true;
            if (productNameFilter != null && !productNameFilter.equals("")
                    && !originalProduct.getName().toLowerCase().contains(productNameFilter.toLowerCase()))
                shouldBeAdded = false;
            if (productDescriptionFilter != null && !productDescriptionFilter.equals("")
                    && !originalProduct.getDescription().toLowerCase().contains(productDescriptionFilter.toLowerCase()))
                shouldBeAdded = false;
            if (brandFilter != null && !brandFilter.equals("")
                    && !originalProduct.getBrand().toLowerCase().contains(brandFilter.toLowerCase()))
                shouldBeAdded = false;
            // TODO: Wrong format of price??
            // TODO: Price filter should be checked!!
            if (priceFilter != null && !priceFilter.equals("")) {
                int price = -1;
                try {
                    price = Integer.parseInt(priceFilter);
                } catch (NumberFormatException ignored) {
                }
                if (price != -1 && (originalProduct.getPrice() != price)) shouldBeAdded = false;
            }
            if (sellerNameOrCompanyFilter != null && !sellerNameOrCompanyFilter.equals("")) {
                boolean hasFoundAnySellerWithTheFilter = false;
                for (Seller seller : originalProduct.getSellers()) {
                    if (seller.getCompanyDetails().toLowerCase().contains(sellerNameOrCompanyFilter.toLowerCase())) {
                        hasFoundAnySellerWithTheFilter = true;
                    }
                }
                if (!hasFoundAnySellerWithTheFilter) shouldBeAdded = false;
            }
            if (onlyAvailableProductsFilter != null && onlyAvailableProductsFilter.equals("true")) {
                if (originalProduct.getNumberAvailable() < 1) shouldBeAdded = false;
            }
            if (shouldBeAdded) products.add(originalProduct);
        }
        productsAdapter.notifyDataSetChanged();
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

    String productNameFilter = "";
    String productDescriptionFilter = "";
    String brandFilter = "";
    String priceFilter = ""; // empty is no filter
    String sellerNameOrCompanyFilter = "";
    String onlyAvailableProductsFilter = "false"; // empty == false here

    public void filterTapped(View view) {
        Intent intent = new Intent(this, FilterProductsActivity.class);
        intent.putExtra("categoryID", categoryID);
        intent.putExtra("productName", productNameFilter);
        intent.putExtra("productDescription", productDescriptionFilter);
        intent.putExtra("brand", brandFilter);
        intent.putExtra("price", priceFilter);
        intent.putExtra("sellerNameOrCompany", sellerNameOrCompanyFilter);
        intent.putExtra("onlyAvailableProducts", onlyAvailableProductsFilter);
        startActivityForResult(intent, 2);
    }
}