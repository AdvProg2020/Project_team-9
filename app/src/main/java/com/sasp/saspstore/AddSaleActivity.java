package com.sasp.saspstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.AddSaleBySellerRequest;
import com.sasp.saspstore.model.Coupon;
import com.sasp.saspstore.model.DeliveryStatus;
import com.sasp.saspstore.model.EditSaleBySellerRequest;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Sale;
import com.sasp.saspstore.model.SaleStatus;
import com.sasp.saspstore.model.Seller;
import com.sasp.saspstore.model.Status;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class AddSaleActivity extends AppCompatActivity {

    EditText txtDiscountAmount;
    TextView txtChooseProducts;
    ArrayList<Product> selectedProducts = new ArrayList<>();
    // TODO: Error if default...?
    // TODO: Error if start time after end time...?
    LocalDateTime startDate = LocalDateTime.now();
    LocalDateTime endDate = LocalDateTime.now();

    String saleID;

    // TODO: We assume only seller is able to access this activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        txtDiscountAmount = (EditText) findViewById(R.id.addSale_discountAmount);
        txtChooseProducts = (TextView) findViewById(R.id.addSale_txtChooseProducts);

        FloatingActionButton fab = findViewById(R.id.addSale_fab);
        fab.setImageBitmap(textAsBitmap("✓", 40, Color.WHITE));
        fab.setOnClickListener(view -> {
            try {
                // TODO: Check to be valid and in range...
                int discountAmount = Integer.parseInt(txtDiscountAmount.getText().toString());
                if (saleID.equals("")) {
                    Sale sale = new Sale(DataManager.getNewId(), selectedProducts, SaleStatus.CONFIRMED, discountAmount, startDate, endDate, (Seller) DataManager.shared().getLoggedInAccount(), DeliveryStatus.ORDERED);
                    AddSaleBySellerRequest request = new AddSaleBySellerRequest(DataManager.getNewId(), (Seller) DataManager.shared().getLoggedInAccount(), sale);
                    DataManager.shared().addRequest(request);
                    Toast.makeText(this, "درخواست افزودن حراج با موفقیت به مدیر ارسال شد", Toast.LENGTH_LONG).show();
                } else {
                    Sale oldSale = DataManager.shared().getSaleWithId(saleID);
                    Sale newSale = new Sale(oldSale.getOffId(), selectedProducts, oldSale.getSaleStatus(), discountAmount,
                            startDate, endDate, oldSale.getSeller(), DeliveryStatus.ORDERED);
                    EditSaleBySellerRequest request = new EditSaleBySellerRequest(DataManager.getNewId(),
                            (Seller) DataManager.shared().getLoggedInAccount(), oldSale, newSale);
                    DataManager.shared().addRequest(request);
                    Toast.makeText(this, "درخواست ویرایش حراج با موفقیت به مدیر ارسال شد", Toast.LENGTH_LONG).show();
                }
                finish();
            } catch (Exception ignored) {
                // TODO: Here
            }
        });

        Intent intent = getIntent();
        saleID = intent.getStringExtra("saleID");
        if (saleID == null) {
            saleID = "";
        } else {
            Sale sale = DataManager.shared().getSaleWithId(saleID);
            txtDiscountAmount.setText(Integer.toString(sale.getDiscountAmount()));
            startDate = sale.getStartTime();
            endDate = sale.getEndTime();
            selectedProducts = sale.getProducts();
            txtChooseProducts.setText("انتخاب محصولات (" + selectedProducts.size() + " عدد انتخاب شده‌اند)");
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
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.addcoupon_memu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profilemenu_btnEdit) {
            try {
                // TODO: Check to be valid and in range...
                int discountAmount = Integer.parseInt(txtDiscountAmount.getText().toString());
                if (saleID.equals("")) {
                    Sale sale = new Sale(DataManager.getNewId(), selectedProducts, SaleStatus.CONFIRMED, discountAmount, startDate, endDate, (Seller) DataManager.shared().getLoggedInAccount(), DeliveryStatus.ORDERED);
                    AddSaleBySellerRequest request = new AddSaleBySellerRequest(DataManager.getNewId(), (Seller) DataManager.shared().getLoggedInAccount(), sale);
                    DataManager.shared().addRequest(request);
                    Toast.makeText(this, "درخواست افزودن حراج با موفقیت به مدیر ارسال شد", Toast.LENGTH_LONG).show();
                } else {
                    Sale oldSale = DataManager.shared().getSaleWithId(saleID);
                    Sale newSale = new Sale(oldSale.getOffId(), selectedProducts, oldSale.getSaleStatus(), discountAmount,
                            startDate, endDate, oldSale.getSeller(), DeliveryStatus.ORDERED);
                    EditSaleBySellerRequest request = new EditSaleBySellerRequest(DataManager.getNewId(),
                            (Seller) DataManager.shared().getLoggedInAccount(), oldSale, newSale);
                    DataManager.shared().addRequest(request);
                    Toast.makeText(this, "درخواست ویرایش حراج با موفقیت به مدیر ارسال شد", Toast.LENGTH_LONG).show();
                }
                finish();
            } catch (Exception ignored) {
                // TODO: Here
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        }
    }
}