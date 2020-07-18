package com.sasp.saspstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Coupon;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Log;
import com.sasp.saspstore.ui.home.EditProfileActivity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AllCouponsActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Coupon> adapter;

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_coupons);

        listView = findViewById(R.id.allCouponsList);
        if (checkForCustomerSpecificCoupons()) return;

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, DataManager.shared().getAllCoupons());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.coupon_editing_dialog, null);
            dialogBuilder.setView(dialogView);

            Coupon coupon = (Coupon) listView.getItemAtPosition(position);

            final TextView firstTextView = (TextView) dialogView.findViewById(R.id.couponAlertTextView);
            final TextView secondTextView = (TextView) dialogView.findViewById(R.id.couponAlertProductsTextView);

            StringBuilder first = new StringBuilder("کد: " + coupon.getId()).append("\n");
            first.append("زمان شروع: ").append(coupon.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n");
            first.append("زمان پایان: ").append(coupon.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            // TODO: List mahsulat and usages count???
            firstTextView.setText(first);
            secondTextView.setText("");

            final EditText editDiscountPercent = (EditText) dialogView.findViewById(R.id.editDiscountPercent);
            final EditText editMaximumDiscount = (EditText) dialogView.findViewById(R.id.editMaximumDiscount);
            editDiscountPercent.setText(Integer.toString(coupon.getDiscountPercent()));
            editMaximumDiscount.setText(Integer.toString(coupon.getMaximumDiscount()));
            dialogBuilder.setTitle("ویرایش کد تخفیف").setMessage("").setPositiveButton("ثبت تغییرات", (dialog, whichButton) -> {
                try {
                    int discountPercent = Integer.parseInt(editDiscountPercent.getText().toString());
                    int maximumDiscount = Integer.parseInt(editMaximumDiscount.getText().toString());
                    if (discountPercent >= 0 && discountPercent <= 100) {
                        coupon.setDiscountPercent(discountPercent);
                        coupon.setMaximumDiscount(maximumDiscount);
                        DataManager.shared().syncCoupons();
                        adapter.notifyDataSetChanged();
                    } else {
                        // TODO: ??
                    }
                } catch (Exception ignored) {
                    // TODO: Here!!!
                }
            }).setNegativeButton("حذف کد تخفیف", (dialog, whichButton) -> {
                DataManager.shared().removeCoupon((Coupon) listView.getItemAtPosition(position));
                adapter.notifyDataSetChanged();
            }).setNeutralButton("بازگشت", null);
            AlertDialog b = dialogBuilder.create();
            b.show();
        });
        Account account = DataManager.shared().getLoggedInAccount();
        FloatingActionButton fab = findViewById(R.id.allCoupons_fab);
        fab.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        Intent intent = getIntent();
        String customerUsername = intent.getStringExtra("customerUsername");
        if (customerUsername != null && !customerUsername.equals("")) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setImageBitmap(textAsBitmap("+", 40, Color.WHITE));
            if (account instanceof Administrator)
                fab.setOnClickListener(view -> startActivity(new Intent(AllCouponsActivity.this, AddCouponActivity.class)));
        }
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
    protected void onResume() {
        super.onResume();
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    private boolean checkForCustomerSpecificCoupons() {
        Intent intent = getIntent();
        String customerUsername = intent.getStringExtra("customerUsername");
        if (customerUsername != null && !customerUsername.equals("")) {
            Account account = DataManager.shared().getAccountWithGivenUsername(customerUsername);
            if (account instanceof Customer) {
                ArrayList<Coupon> coupons = new ArrayList<>();
                for (Coupon coupon : DataManager.shared().getAllCoupons()) {
                    Integer usagesCount = coupon.getRemainingUsagesCount().get(customerUsername);
                    if (usagesCount != null && usagesCount > 0) {
                        coupons.add(coupon);
                    }
                }
                ArrayAdapter<Coupon> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                        android.R.id.text1, coupons);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((adapterView, view, i, l) -> {
                    Coupon coupon = (Coupon) listView.getItemAtPosition(i);
                    StringBuilder stringBuilder = new StringBuilder("کد: " + coupon.getId()).append("\n");
                    stringBuilder.append("زمان شروع: ").append(coupon.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                            .append("\n");
                    stringBuilder.append("زمان پایان: ").append(coupon.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    new AlertDialog.Builder(AllCouponsActivity.this).setTitle("اطلاعات کد تخفیف")
                            .setMessage(stringBuilder).setNeutralButton("بازگشت", null).show();
                });
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Intent intent = getIntent();
//        String customerUsername = intent.getStringExtra("customerUsername");
//        if (customerUsername != null && !customerUsername.equals("")) return false;
//        getMenuInflater().inflate(R.menu.allcoupons_menu, menu);
//        return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.allcouponsmenu_btnAdd) {
            startActivity(new Intent(this, AddCouponActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}