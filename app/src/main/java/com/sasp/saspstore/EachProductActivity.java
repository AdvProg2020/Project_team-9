package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Cart;
import com.sasp.saspstore.model.Category;
import com.sasp.saspstore.model.Comment;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Seller;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EachProductActivity extends AppCompatActivity {

    ImageView eachProductImageView;
    TextView eachProductTitle;
    TextView eachProductBrand;
    TextView eachProductPriceAndDiscountPercent;
    TextView eachProductNumberAvailable;
    TextView eachProductDescription;
    TextView eachProductDateCreated;
    TextView eachProductScore;
    EditText eachProductCompareProductID;

    Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_product);

        eachProductImageView = findViewById(R.id.eachProductImageView);
        eachProductTitle = findViewById(R.id.eachProductTitle);
        eachProductBrand = (TextView) findViewById(R.id.eachProductBrand);
        eachProductPriceAndDiscountPercent = (TextView) findViewById(R.id.eachProductPriceAndDiscountPercent);
        eachProductNumberAvailable = (TextView) findViewById(R.id.eachProductNumberAvailable);
        eachProductDescription = (TextView) findViewById(R.id.eachProductDescription);
        eachProductDateCreated = (TextView) findViewById(R.id.eachProductDateCreated);
        eachProductScore = (TextView) findViewById(R.id.eachProductScore);
        eachProductCompareProductID = findViewById(R.id.eachProductCompareProductID);

        Intent intent = getIntent();
        String productID = intent.getStringExtra("productID");
        if (productID == null || productID.equals("")) return;
        currentProduct = DataManager.shared().getProductWithId(productID);

        // TODO: ImageView file source??
        Bitmap bitmap = new ImageSaver(this).setFileName(productID + ".png").setDirectoryName("images").load();
        eachProductImageView.setImageBitmap(bitmap);
        eachProductTitle.setText(currentProduct.getName());
        eachProductBrand.setText("برند: " + currentProduct.getName());
        if (currentProduct.getDiscountPercent() != 0) {
            eachProductPriceAndDiscountPercent.setText("مبلغ اصلی: " + currentProduct.getPrice() + " تومان، با " +
                    currentProduct.getDiscountPercent() + " درصد تخفیف، " +
                    (currentProduct.getPrice() * (100 - currentProduct.getDiscountPercent()) / 100) + " تومان");
        } else {
            eachProductPriceAndDiscountPercent.setText("مبلغ اصلی: " + currentProduct.getPrice() + " تومان");
        }
        eachProductNumberAvailable.setText(Integer.toString(currentProduct.getNumberAvailable()) + " عدد موجود است");
        eachProductDescription.setText(currentProduct.getDescription());
        eachProductDateCreated.setText("اضافه شده در " + currentProduct.getDateCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        starRating((int) currentProduct.getAverageScore());
    }

    private void starRating(int score) {
        switch (score) {
            case 1:
                eachProductScore.setText("★☆☆☆☆");
                break;
            case 2:
                eachProductScore.setText("★★☆☆☆");
                break;
            case 3:
                eachProductScore.setText("★★★☆☆");
                break;
            case 4:
                eachProductScore.setText("★★★★☆");
                break;
            case 5:
                eachProductScore.setText("★★★★★");
                break;
            default:
                eachProductScore.setText("☆☆☆☆☆");
                break;
        }
    }

    public void showCommentsTapped(View view) {
        Intent intent = new Intent(this, CommentsListActivity.class);
        intent.putExtra("productID", currentProduct.getProductId());
        startActivity(intent);
    }

    public void submitScoreTapped(View view) {
        Account account = DataManager.shared().getLoggedInAccount();
        if (account instanceof Customer) {
            Customer customer = (Customer) account;
            if (customer.hasPurchasedProduct(currentProduct)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("لطفا امتیاز مورد نظر خود را از صفر تا پنج وارد نمایید");
                // TODO: Wrong format or out of range number??
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("ثبت", (dialog, whichButton) -> {
                    currentProduct.addScore(Integer.parseInt(input.getText().toString()), customer);
                    DataManager.saveData();
                    starRating((int) currentProduct.getAverageScore());
                });
                alert.setNegativeButton("بازگشت", null);
                alert.show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("خطا")
                        .setMessage("برای ثبت امتیاز ابتدا باید کالا را خریداری کرده باشید")
                        .setNeutralButton("بازگشت", null)
                        .show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("خطا")
                    .setMessage("ابتدا توسط حساب کاربری خریدار وارد شوید")
                    .setNeutralButton("بازگشت", null)
                    .show();
        }
    }

    public void submitCommentTapped(View view) {
        Account account = DataManager.shared().getLoggedInAccount();
        if (account instanceof Customer) {
            Customer customer = (Customer) account;
            if (customer.hasPurchasedProduct(currentProduct)) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.coupon_editing_dialog, null);
                dialogBuilder.setView(dialogView);
                final TextView firstTextView = (TextView) dialogView.findViewById(R.id.couponAlertTextView);
                final TextView secondTextView = (TextView) dialogView.findViewById(R.id.couponAlertProductsTextView);
                firstTextView.setText("لطفا نظر خود را وارد نمایید");
                secondTextView.setText("");
                final EditText firstEditText = (EditText) dialogView.findViewById(R.id.editDiscountPercent);
                final EditText secondEditText = (EditText) dialogView.findViewById(R.id.editMaximumDiscount);
                dialogBuilder.setTitle("ثبت نظر").setMessage("").setPositiveButton("ثبت نظر", (dialog, whichButton) -> {
                    String title = firstEditText.getText().toString();
                    String text = secondEditText.getText().toString();
                    Comment comment = new Comment(customer, currentProduct, title, text);
                    currentProduct.addComment(comment);
                    DataManager.saveData();
                }).setNeutralButton("بازگشت", null);
                AlertDialog b = dialogBuilder.create();
                b.show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("خطا")
                        .setMessage("برای ثبت نظر ابتدا باید کالا را خریداری کرده باشید")
                        .setNeutralButton("بازگشت", null)
                        .show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("خطا")
                    .setMessage("ابتدا توسط حساب کاربری خریدار وارد شوید")
                    .setNeutralButton("بازگشت", null)
                    .show();
        }
    }

    public void addToCartTapped(View view) {
        if (currentProduct.getSellers().size() < 2) {
            addToCart();
            return;
        }
        final EditText editText = new EditText(this);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("انتخاب فروشنده");
        StringBuilder stringBuilder = new StringBuilder("از میان فروشنده‌های زیر، فروشنده مطلوب را انتخاب کنید: (در صورت اشتباه بودن نام فروشنده، به تصادف انتخاب می‌شود) ");
        for (Seller seller : currentProduct.getSellers()) {
            stringBuilder.append("\n").append(seller.getUsername());
        }
        alertDialogBuilder.setMessage(stringBuilder.toString());
        alertDialogBuilder.setView(editText);
        alertDialogBuilder.setPositiveButton("افزودن به سبد خرید", (dialogInterface, i) -> {
            Account account = DataManager.shared().getAccountWithGivenUsername(editText.getText().toString());
            if (account instanceof Seller) currentProduct.setCurrentSeller((Seller) account);
            addToCart();
        });
        alertDialogBuilder.setNeutralButton("بازگشت", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void addToCart() {
        Account account = DataManager.shared().getLoggedInAccount();
        Cart cart;
        if (account instanceof Customer) cart = ((Customer) account).getCart();
        else cart = DataManager.shared().getTemporaryCart();
        cart.addProduct(currentProduct);
        DataManager.saveData();
        Toast.makeText(this, "کالا با موفقیت به سبد خرید افزوده شد", Toast.LENGTH_LONG).show();
    }


    public void compareTapped(View view) {
        if (eachProductCompareProductID.getText().toString().equals("")) return;
        Product product = DataManager.shared().getProductWithName(eachProductCompareProductID.getText().toString());
        if (product == null) return;
        Category category = product.getCategory();
        if (!category.getId().equals(currentProduct.getCategory().getId())) {
            new AlertDialog.Builder(EachProductActivity.this).setTitle("خطا")
                    .setMessage("تنها دو محصول از یک گروه با یک‌دیگر قابل مقایسه‌اند")
                    .setNeutralButton("بازگشت", null).show();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("نام: ").append("\n");
        stringBuilder.append(currentProduct.getName()).append("\n");
        stringBuilder.append(product.getName()).append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("برند: ").append("\n");
        stringBuilder.append(currentProduct.getBrand()).append("\n");
        stringBuilder.append(product.getBrand()).append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("قیمت: ").append("\n");
        stringBuilder.append(currentProduct.getPrice()).append("\n");
        stringBuilder.append(product.getPrice()).append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("درصد تخفیف").append("\n");
        stringBuilder.append(currentProduct.getDiscountPercent()).append("\n");
        stringBuilder.append(product.getDiscountPercent()).append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("تعداد موجود: ").append("\n");
        stringBuilder.append(currentProduct.getNumberAvailable()).append("\n");
        stringBuilder.append(product.getNumberAvailable()).append("\n");
        stringBuilder.append("\n");
        for (String feature : category.getUniqueFeatures()) {
            stringBuilder.append(feature).append(": ").append("\n");
            stringBuilder.append(currentProduct.getFeatures().get(feature)).append("\n");
            stringBuilder.append(product.getFeatures().get(feature)).append("\n").append("\n");
        }
        stringBuilder.append("توضیحات: ").append("\n");
        stringBuilder.append(currentProduct.getDescription()).append("\n");
        stringBuilder.append(product.getDescription());
        new AlertDialog.Builder(EachProductActivity.this).setTitle("مقایسه محصول")
                .setMessage(stringBuilder.toString())
                .setNeutralButton("بازگشت", null).show();
    }

    public void seeFeaturesTapped(View view) {
        Category category = currentProduct.getCategory();
        if (category.getUniqueFeatures().size() != 2 || currentProduct.getFeatures().keySet().size() != 2) {
            Toast.makeText(this, "ویژگی‌های دسته‌بندی موجود نیست", Toast.LENGTH_LONG).show();
            return;
        }
        new AlertDialog.Builder(EachProductActivity.this).setTitle("ویژگی‌های دسته‌بندی")
                .setMessage(category.getUniqueFeatures().get(0) + ":\n" + currentProduct.getFeatures().get(category.getUniqueFeatures().get(0))
                        + "\n\n" + category.getUniqueFeatures().get(1) + ":\n" + currentProduct.getFeatures().get(category.getUniqueFeatures().get(1)))
                .setNeutralButton("بازگشت", null).show();
    }
}