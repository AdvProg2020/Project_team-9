package com.sasp.saspstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.controller.ProductsAdapter;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.ui.home.EditProfileActivity;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    ArrayList<Product> selectedProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Intent intent = getIntent();
        String productIDs = intent.getStringExtra("productIDs");
        ArrayList<Product> products;
        if (productIDs != null) {
            products = new ArrayList<>();
            String[] parts = productIDs.split(";;;;");
            for (String productID : parts)
                products.add(DataManager.shared().getProductWithId(productID));
        } else products = DataManager.shared().getAllProducts();

        GridView gridView = (GridView)findViewById(R.id.productsListGridView);
        // TODO: Does the second line work in converting array???
        Product[] productsArray = products.toArray(new Product[0]);
        final ProductsAdapter productsAdapter = new ProductsAdapter(this, productsArray);
        gridView.setAdapter(productsAdapter);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Product product = productsArray[position];
            if (product.isSelected()) {
                product.setSelected(false);
                selectedProducts.remove(product);
            } else {
                product.setSelected(true);
                selectedProducts.add(product);
            }
            productsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.getproducts_menu, menu);
        return true;
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