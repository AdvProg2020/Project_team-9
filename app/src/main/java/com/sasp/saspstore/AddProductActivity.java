package com.sasp.saspstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.AddProductBySellerRequest;
import com.sasp.saspstore.model.Category;
import com.sasp.saspstore.model.EditProductBySellerRequest;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Seller;
import com.sasp.saspstore.model.Status;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AddProductActivity extends AppCompatActivity {

    EditText txtProductName;
    EditText txtBrandName;
    EditText txtPrice;
    EditText txtDiscountPercent;
    EditText txtAvailableCount;
    EditText txtProductDescription;
    EditText txtFirstFeature;
    EditText txtSecondFeature;
    TextView lblFirstFeature;
    TextView lblSecondFeature;
    ImageView selectedImageView;
    String productID = "";
    String categoryID = "";

    // TODO: Assumed ONLY SELLERS can enter this activity. Else, crash :)

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        findViewsById();
        Intent receivedIntent = getIntent();
        productID = receivedIntent.getStringExtra("productID");
        categoryID = receivedIntent.getStringExtra("categoryID");
        Category category = DataManager.shared().getCategoryWithId(categoryID);
        if (productID != null) {
            Product product = DataManager.shared().getProductWithId(productID);
            if (product != null) {
                txtProductName.setText(product.getName());
                txtBrandName.setText(product.getBrand());
                txtPrice.setText(Integer.toString(product.getPrice()));
                txtDiscountPercent.setText(Integer.toString(product.getDiscountPercent()));
                txtAvailableCount.setText(Integer.toString(product.getNumberAvailable()));
                txtProductDescription.setText(product.getDescription());
                if (category == null) category = product.getCategory();
                if (category != null && category.getUniqueFeatures().size() == 2) {
                    lblFirstFeature.setText(category.getUniqueFeatures().get(0));
                    lblSecondFeature.setText(category.getUniqueFeatures().get(1));
                }
            } else productID = "";
        } else productID = "";

        FloatingActionButton fab = findViewById(R.id.submitAddProduct_fab);
        fab.setImageBitmap(textAsBitmap("✓", 40, Color.WHITE));
        Category finalCategory = category;
        fab.setOnClickListener(view -> {
            int price = Integer.parseInt(txtPrice.getText().toString());
            int discountPercent = Integer.parseInt(txtDiscountPercent.getText().toString());
            int numberAvailable = Integer.parseInt(txtAvailableCount.getText().toString());
            String newProductID = DataManager.getNewId();
            Drawable drawable = Drawable.createFromStream(imageInputStream, newProductID);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            new ImageSaver(this).setFileName(newProductID + ".png").setDirectoryName("images").save(bitmap);
            HashMap<String, String> hashMap = new HashMap<>();
            if (finalCategory != null) {
                hashMap.put(finalCategory.getUniqueFeatures().get(0), txtFirstFeature.getText().toString());
                hashMap.put(finalCategory.getUniqueFeatures().get(1), txtSecondFeature.getText().toString());
            }
            if (productID.equals("")) {
                // TODO: Integer validation...!
                // TODO: Validation of discount percent!
                ArrayList<Seller> sellers = new ArrayList<>();
                sellers.add((Seller) DataManager.shared().getLoggedInAccount());
                // TODO: IMPORTANT: Features of product with values!!
                Product product = new Product(newProductID, Status.CONFIRMED, txtProductName.getText().toString(),
                        txtBrandName.getText().toString(), price, discountPercent, sellers, numberAvailable,
                        DataManager.shared().getCategoryWithId(categoryID), txtProductDescription.getText().toString(),
                        LocalDateTime.now(), hashMap);
                AddProductBySellerRequest request = new AddProductBySellerRequest(DataManager.getNewId(), (Seller) DataManager.shared().getLoggedInAccount(), product);
                DataManager.shared().addRequest(request);
                Toast.makeText(this, "درخواست افزودن کالا با موفقیت به مدیر ارسال شد", Toast.LENGTH_LONG).show();
            } else {
                Product oldProduct = DataManager.shared().getProductWithId(productID);
                // TODO: features array in the newProduct
                Product newProduct = new Product(oldProduct.getProductId(), oldProduct.getStatus(), txtProductName.getText().toString(),
                        txtBrandName.getText().toString(), price, discountPercent, oldProduct.getSellers(),
                        numberAvailable, oldProduct.getCategory(),
                        txtProductDescription.getText().toString(), oldProduct.getDateCreated(), hashMap);
                EditProductBySellerRequest request = new EditProductBySellerRequest(DataManager.getNewId(), (Seller) DataManager.shared().getLoggedInAccount(), oldProduct, newProduct);
                DataManager.shared().addRequest(request);
                Toast.makeText(this, "درخواست ویرایش کالا با موفقیت به مدیر ارسال شد", Toast.LENGTH_LONG).show();
            }
            finish();
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

    private void findViewsById() {
        txtProductName = findViewById(R.id.addProduct_txtProductName);
        txtBrandName = findViewById(R.id.addProduct_txtBrandName);
        txtPrice = findViewById(R.id.addProduct_txtPrice);
        txtDiscountPercent = findViewById(R.id.addProduct_txtDiscountPercent);
        txtAvailableCount = findViewById(R.id.addProduct_txtAvailableCount);
        txtProductDescription = findViewById(R.id.addProduct_txtProductDescription);
        txtFirstFeature = findViewById(R.id.addProduct_txtFirstFeature);
        txtSecondFeature = findViewById(R.id.addProduct_txtSecondFeature);
        lblFirstFeature = findViewById(R.id.addProduct_lblFirstFeature);
        lblSecondFeature = findViewById(R.id.addProduct_lblSecondFeature);
    }

    public static final int PICK_IMAGE = 10;

    public void selectPictureTapped(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "انتخاب عکس"), PICK_IMAGE);
    }

    InputStream imageInputStream;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // TODO: The next line???
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data == null) return;
            try {
                imageInputStream = getContentResolver().openInputStream(Objects.requireNonNull(data.getData()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}