package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Category;
import com.sasp.saspstore.model.Comment;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Product;

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

    Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_product);

        eachProductImageView = (ImageView) findViewById(R.id.eachProductImageView);
        eachProductTitle = (TextView) findViewById(R.id.eachProductTitle);
        eachProductBrand = (TextView) findViewById(R.id.eachProductBrand);
        eachProductPriceAndDiscountPercent = (TextView) findViewById(R.id.eachProductPriceAndDiscountPercent);
        eachProductNumberAvailable = (TextView) findViewById(R.id.eachProductNumberAvailable);
        eachProductDescription = (TextView) findViewById(R.id.eachProductDescription);
        eachProductDateCreated = (TextView) findViewById(R.id.eachProductDateCreated);
        eachProductScore = (TextView) findViewById(R.id.eachProductScore);

        Intent intent = getIntent();
        String productID = intent.getStringExtra("productID");
        if (productID == null || productID.equals("")) return;
        currentProduct = DataManager.shared().getProductWithId(productID);

        // TODO: ImageView...
        eachProductTitle.setText(currentProduct.getName());
        eachProductBrand.setText(currentProduct.getName());
        if (currentProduct.getDiscountPercent() != 0) {
            eachProductPriceAndDiscountPercent.setText("مبلغ اصلی: " + currentProduct.getPrice() + " تومان، با " +
                    currentProduct.getDiscountPercent() + " درصد تخفیف، " +
                    (currentProduct.getPrice() * (100 - currentProduct.getDiscountPercent()) / 100) + " تومان");
        } else {
            eachProductPriceAndDiscountPercent.setText("مبلغ اصلی: " + currentProduct.getPrice() + " تومان");
        }
        eachProductPriceAndDiscountPercent.setText("");
        eachProductNumberAvailable.setText(currentProduct.getNumberAvailable());
        eachProductDescription.setText(currentProduct.getDescription());
        eachProductDateCreated.setText(currentProduct.getDateCreated().format(DateTimeFormatter.BASIC_ISO_DATE));
        switch ((int) currentProduct.getAverageScore()) {
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
        eachProductScore.setText("" + currentProduct.getAverageScore());
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
                    eachProductScore.setText("" + currentProduct.getAverageScore());
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
}