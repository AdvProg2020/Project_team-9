package com.sasp.saspstore.view.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sasp.saspstore.R;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Category;

public class FilterProductsActivity extends AppCompatActivity {
    
    EditText txtProductName;
    EditText txtProductDescription;
    EditText txtBrand;
    EditText txtPrice;
    // TODO: Invalid number format as price??
    EditText txtSellerNameOrCompany;
    EditText txtFirstFeature;
    EditText txtSecondFeature;
    TextView lblFirstFeature;
    TextView lblSecondFeature;
    Switch switchOnlyAvailableProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_products);

        setTitle("");

        txtProductName = findViewById(R.id.filterProduct_txtProductName);
        txtProductDescription = findViewById(R.id.filterProduct_txtProductDescription);
        txtBrand = findViewById(R.id.filterProduct_txtBrand);
        txtPrice = findViewById(R.id.filterProduct_txtPrice);
        txtSellerNameOrCompany = findViewById(R.id.filterProduct_txtSellerNameOrCompany);
        txtFirstFeature = findViewById(R.id.filterProduct_txtFirstFeature);
        txtSecondFeature = findViewById(R.id.filterProduct_txtSecondFeature);
        lblFirstFeature = findViewById(R.id.filterProduct_lblFirstFeature);
        lblSecondFeature = findViewById(R.id.filterProduct_lblSecondFeature);
        switchOnlyAvailableProducts = findViewById(R.id.filterProduct_switchOnlyAvailableProducts);

        populateDataUsingReceivedIntent();
    }

    private void populateDataUsingReceivedIntent() {
        Intent intent = getIntent();
        String categoryID = intent.getStringExtra("categoryID");
        if (categoryID != null) {
            Category category = DataManager.shared().getCategoryWithId(categoryID);
            if (category != null && category.getUniqueFeatures().size() == 2) {
                lblFirstFeature.setVisibility(View.VISIBLE);
                lblSecondFeature.setVisibility(View.VISIBLE);
                lblFirstFeature.setText(category.getUniqueFeatures().get(0));
                lblSecondFeature.setText(category.getUniqueFeatures().get(1));
            } else {
                lblFirstFeature.setVisibility(View.GONE);
                lblSecondFeature.setVisibility(View.GONE);
            }
        }
        String productName = intent.getStringExtra("productName");
        if (productName != null) txtProductName.setText(productName);
        String productDescription = intent.getStringExtra("productDescription");
        if (productDescription != null) txtProductDescription.setText(productDescription);
        String brand = intent.getStringExtra("brand");
        if (brand != null) txtBrand.setText(brand);
        String price = intent.getStringExtra("price");
        if (price != null) txtPrice.setText(price);
        String sellerNameOrCompany = intent.getStringExtra("sellerNameOrCompany");
        if (sellerNameOrCompany != null) txtSellerNameOrCompany.setText(sellerNameOrCompany);
        String firstFeature = intent.getStringExtra("firstFeature");
        if (firstFeature != null) txtFirstFeature.setText(firstFeature);
        String secondFeature = intent.getStringExtra("secondFeature");
        if (secondFeature != null) txtSecondFeature.setText(secondFeature);
        String onlyAvailableProducts = intent.getStringExtra("onlyAvailableProducts");
        if (onlyAvailableProducts != null && onlyAvailableProducts.equals("true")) switchOnlyAvailableProducts.setChecked(true);
        else switchOnlyAvailableProducts.setChecked(false);
    }

    public void submitTapped(View view) {
        submit();
    }

    private void submit() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("productName", txtProductName.getText().toString());
        resultIntent.putExtra("productDescription", txtProductDescription.getText().toString());
        resultIntent.putExtra("brand", txtBrand.getText().toString());
        resultIntent.putExtra("price", txtPrice.getText().toString());
        resultIntent.putExtra("sellerNameOrCompany", txtSellerNameOrCompany.getText().toString());
        resultIntent.putExtra("firstFeature", txtFirstFeature.getText().toString());
        resultIntent.putExtra("secondFeature", txtSecondFeature.getText().toString());
        resultIntent.putExtra("onlyAvailableProducts", switchOnlyAvailableProducts.isChecked() ? "true" : "false");
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void removeFiltersTapped(View view) {
        txtProductName.setText("");
        txtProductDescription.setText("");
        txtBrand.setText("");
        txtPrice.setText("");
        txtSellerNameOrCompany.setText("");
        txtFirstFeature.setText("");
        txtSecondFeature.setText("");
        switchOnlyAvailableProducts.setChecked(false);
        submit();
    }
}