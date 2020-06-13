package com.sasp.saspstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Coupon;
import com.sasp.saspstore.model.Log;

import java.time.format.DateTimeFormatter;

public class AllCouponsActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_coupons);

        listView = (ListView) findViewById(R.id.allCouponsList);
        ArrayAdapter<Coupon> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, DataManager.shared().getAllCoupons());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.coupon_editing_dialog, null);
            dialogBuilder.setView(dialogView);

            Coupon coupon = (Coupon) listView.getItemAtPosition(position);

            final TextView firstTextView = (TextView) dialogView.findViewById(R.id.couponAlertTextView);
            final TextView secondTextView = (TextView) dialogView.findViewById(R.id.couponAlertProductsTextView);

            firstTextView.setText( "کد: " + coupon.getId());
            StringBuilder first = new StringBuilder( "کد: " + coupon.getId());
            first.append("\n");
            first.append("زمان شروع: ").append(coupon.getStartTime().format(DateTimeFormatter.BASIC_ISO_DATE));
            first.append("زمان پایان: ").append(coupon.getStartTime().format(DateTimeFormatter.BASIC_ISO_DATE));
            // TODO: List mahsulat and usages count???
            firstTextView.setText(first);
            secondTextView.setText("");

            final EditText editDiscountPercent = (EditText) dialogView.findViewById(R.id.editDiscountPercent);
            final EditText editMaximumDiscount = (EditText) dialogView.findViewById(R.id.editMaximumDiscount);

            dialogBuilder.setTitle("ویرایش کد تخفیف").setMessage("").setPositiveButton("ثبت تغییرات", (dialog, whichButton) -> {
                try {
                    int discountPercent = Integer.parseInt(editDiscountPercent.getText().toString());
                    int maximumDiscount = Integer.parseInt(editMaximumDiscount.getText().toString());
                    if (discountPercent >= 0 && discountPercent <= 100) {
                        coupon.setDiscountPercent(discountPercent);
                        coupon.setMaximumDiscount(maximumDiscount);
                    } else {
                        // TODO: ??
                    }
                } catch (Exception ignored) {
                    // TODO: Here!!!
                }
            }).setNegativeButton("حذف کد تخفیف", (dialog, whichButton) -> {
                DataManager.shared().removeCoupon((Coupon) listView.getItemAtPosition(position));
            }).setNeutralButton("بازگشت", null);
            AlertDialog b = dialogBuilder.create();
            b.show();
        });
    }
}