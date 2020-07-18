package com.sasp.saspstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Coupon;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Status;
import com.sasp.saspstore.ui.home.EditProfileActivity;
import com.sasp.saspstore.view.menus.AllProductsMenu;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

public class AddCouponActivity extends AppCompatActivity {

    EditText txtDiscountPercent;
    EditText txtMaximumDiscount;
    TextView txtChooseProducts;
    TextView txtSelectUserCount;
    ArrayList<Product> selectedProducts = new ArrayList<>();
    HashMap<String, Integer> remainingUsagesCount = new HashMap<>();
    // TODO: Error if default...?
    // TODO: Error if start time after end time...?
    LocalDateTime startDate = LocalDateTime.now();
    LocalDateTime endDate = LocalDateTime.now();
    int numberOfUsersSet = 0;

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);

        txtDiscountPercent = findViewById(R.id.addCoupon_discountPercent);
        txtMaximumDiscount = findViewById(R.id.addCoupon_maximumDiscount);
        txtChooseProducts = findViewById(R.id.addCoupon_txtChooseProducts);
        txtSelectUserCount = findViewById(R.id.addCoupon_txtSelectUserCount);

        FloatingActionButton fab = findViewById(R.id.addCoupon_fab);
        fab.setImageBitmap(textAsBitmap("✓", 40, Color.WHITE));
        fab.setOnClickListener(view -> {
            try {
                // TODO: Check to be valid and in range...
                int discountPercent = Integer.parseInt(txtDiscountPercent.getText().toString());
                int maximumDiscount = Integer.parseInt(txtMaximumDiscount.getText().toString());
                Coupon coupon = new Coupon(DataManager.getNewId(), selectedProducts, Status.CONFIRMED, discountPercent, maximumDiscount, startDate, endDate, remainingUsagesCount);
                DataManager.shared().addCoupon(coupon);
                finish();
            } catch (Exception ignored) {
                // TODO: Here
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.addcoupon_memu, menu);
//        return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.profilemenu_btnEdit) {
//            try {
//                // TODO: Check to be valid and in range...
//                int discountPercent = Integer.parseInt(txtDiscountPercent.getText().toString());
//                int maximumDiscount = Integer.parseInt(txtMaximumDiscount.getText().toString());
//                Coupon coupon = new Coupon(DataManager.getNewId(), selectedProducts, Status.CONFIRMED, discountPercent, maximumDiscount, startDate, endDate, remainingUsagesCount);
//                DataManager.shared().addCoupon(coupon);
//                finish();
//            } catch (Exception ignored) {
//                // TODO: Here
//            }
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    public void selectProductsTapped(View view) {
        Intent i = new Intent(this, ProductListActivity.class);
        i.putExtra("openOrSelect", "select");
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String selectedIDs = data.getStringExtra("selectedIDs");
            if (selectedIDs != null) {
                String[] parts = selectedIDs.split(";;;;");
                for (String id : parts)
                    selectedProducts.add(DataManager.shared().getProductWithId(id));
                txtChooseProducts.setText("انتخاب محصولات (" + parts.length + " عدد انتخاب شده‌اند)");
            } else {
                txtChooseProducts.setText("انتخاب محصولات (" + 0 + " عدد انتخاب شده‌اند)");
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            String selectedUsername = data.getStringExtra("selectedUsername");
            if (selectedUsername != null) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("کاربر " + selectedUsername + " چند بار مجاز است از این کد تخفیف استفاده نماید؟");
                // TODO: If this count is not provided at all...?
                // TODO: If invalid number / username / ...?
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("ثبت", (dialog, whichButton) -> {
                    try {
                        remainingUsagesCount.put(selectedUsername, Integer.parseInt(input.getText().toString()));
                        numberOfUsersSet += 1;
                        txtSelectUserCount.setText("انتخاب دسترسی کاربران (" + numberOfUsersSet + " کاربر تاکنون تنظیم شده است)");
                    } catch (Exception ignored) {
                        // TODO: here???
                    }
                });
                // TODO: Does providing null here and then tapping lead to a NullPointerException??
                alert.setNeutralButton("بازگشت", null);
                alert.show();
            }
        } else {
            txtChooseProducts.setText("انتخاب محصولات (" + 0 + " عدد انتخاب شده‌اند)");
        }

    }

    public void setStartTimeTapped(View view) {
        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener((View.OnClickListener) view1 -> {
            DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
            TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
            Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth(),
                    timePicker.getHour(),
                    timePicker.getMinute());

            // TODO: Does it work?
            TimeZone tz = calendar.getTimeZone();
            ZoneId zid = tz.toZoneId();
            startDate = LocalDateTime.ofInstant(calendar.toInstant(), zid);
            alertDialog.dismiss();
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public void setEndTimeTapped(View view) {
        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener((View.OnClickListener) view1 -> {
            DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
            TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
            Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth(),
                    timePicker.getHour(),
                    timePicker.getMinute());

            // TODO: Does it work?
            TimeZone tz = calendar.getTimeZone();
            ZoneId zid = tz.toZoneId();
            endDate = LocalDateTime.ofInstant(calendar.toInstant(), zid);
            alertDialog.dismiss();
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public void selectUserCountTapped(View view) {
        Intent i = new Intent(this, UsersListActivity.class);
        startActivityForResult(i, 2);
    }
}