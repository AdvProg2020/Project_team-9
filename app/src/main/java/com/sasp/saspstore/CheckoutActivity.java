package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Cart;
import com.sasp.saspstore.model.Coupon;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.DeliveryStatus;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.PurchaseLog;
import com.sasp.saspstore.model.Sale;
import com.sasp.saspstore.model.SellLog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class CheckoutActivity extends AppCompatActivity {

    EditText txtPhoneNumber;
    EditText txtAddress;
    TextView txtTotalPrice;
    EditText txtCoupon;
    Button finishButton;
    Button checkCouponButton;

    Coupon coupon;
    Customer customer;

    double priceAfterDiscount;
    double totalPrice;
    HashMap<Product, Integer> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        txtPhoneNumber = findViewById(R.id.checkout_txtPhoneNumber);
        txtAddress = findViewById(R.id.checkout_txtAddress);
        txtTotalPrice = findViewById(R.id.checkout_totalPrice);
        txtCoupon = findViewById(R.id.checkout_txtCoupon);
        finishButton = findViewById(R.id.checkout_finishButton);
        checkCouponButton = findViewById(R.id.checkout_checkCouponButton);

        updateTotalPriceText();
    }

    private void updateTotalPriceText() {
        customer = (Customer) DataManager.shared().getLoggedInAccount();
        Cart cart = ((Customer) (DataManager.shared().getLoggedInAccount())).getCart();
        products = cart.getProducts();
        totalPrice = getTotalPrice(products);
        StringBuilder txtTotalPriceText = new StringBuilder("مبلغ کل (قبل از تخفیف و با اعمال حراج): " + totalPrice + "\n");
        if (coupon == null) {
            coupon = new Coupon(DataManager.getNewId(), new ArrayList<>());
            coupon.setDiscountPercent(0);
            coupon.setMaximumDiscount(0);
        }
        priceAfterDiscount = totalPrice * (1 - (coupon.getDiscountPercent() / 100.0));
        if (totalPrice - priceAfterDiscount > coupon.getMaximumDiscount()) {
            priceAfterDiscount = totalPrice - coupon.getMaximumDiscount();
        }
        if (totalPrice > 1000) {
            priceAfterDiscount -= 100;
            txtTotalPriceText.append("چون بیش از ۱۰۰۰ دلار خرج کرده اید، یک تخفیف ۱۰۰ دلاری برای شما در نظر گرفته شده است!!").append("\n");
        }
        txtTotalPriceText.append("میزان تخفیف: ").append(totalPrice - priceAfterDiscount).append("\n");
        txtTotalPriceText.append("مبلغ قابل پرداخت: ").append(priceAfterDiscount);
        if (customer.getCredit() < priceAfterDiscount) {
            txtTotalPriceText.append("شما ").append(customer.getCredit()).append(" دلار در حساب خود دارید و متاسفانه نمی‌توانید هزینه این کالاها را پرداخت کنید.");
            finishButton.setVisibility(View.GONE);
        } else {
            finishButton.setVisibility(View.VISIBLE);
        }
        txtTotalPrice.setText(txtTotalPriceText);
    }

    private double getTotalPrice(HashMap<Product, Integer> products) {
        Iterator it = products.entrySet().iterator();
        double totalPrice = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            double price = ((Product) pair.getKey()).getPrice() * (int) pair.getValue();
            if (((Product) pair.getKey()).getNumberAvailable() > 0) {
                for (Sale sale : DataManager.shared().getAllSales()) {
                    if (sale.getProducts().contains(pair.getKey()) && sale.getStartTime().isBefore(LocalDateTime.now()) && sale.getEndTime().isAfter(LocalDateTime.now())) {
                        price -= sale.getDiscountAmount() * (int) pair.getValue();
                        if (price < 1) price = 1;
                    }
                }
                totalPrice += price;
            }
        }
        return totalPrice;
    }

    public void checkCouponTapped(View view) {
        String couponCode = txtCoupon.getText().toString();
        if (couponCode.equals("")) return;
        if (couponCode.equals("chance")) couponByChance();
        else {
            coupon = DataManager.shared().getCouponWithId(couponCode);
            if (checkCouponValidity()) return;
            if (checkCouponUsage()) return;
        }
        affectCoupon();
    }

    private void affectCoupon() {
        txtCoupon.setVisibility(View.GONE);
        checkCouponButton.setVisibility(View.GONE);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("ثبت کد تخفیف");
        alertDialog.setMessage("کد تخفیف شما با موفقیت اعمال شد");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
        updateTotalPriceText();
    }

    private boolean checkCouponUsage() {
        if (coupon.getRemainingUsagesCount().get(customer.getUsername()) < 1) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("شما نمی‌توانید بار دیگر از این کد استفاده نمایید");
            alertDialog.setMessage("تعداد دفعات مجاز استفاده شما از این کد به پایان رسیده است. لطفا کد دیگری را وارد نمایید");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            coupon = null;
            return true;
        }
        return false;
    }

    private boolean checkCouponValidity() {
        if (coupon == null || coupon.getStartTime().isAfter(LocalDateTime.now()) || coupon.getEndTime().isBefore(LocalDateTime.now())) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("کد تحفیف نامعتبر است");
            alertDialog.setMessage("لطفا دوباره تلاش نمایید");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            coupon = null;
            return true;
        }
        return false;
    }

    private void couponByChance() {
        Random random = new Random();
        int r = random.nextInt(10);
        if (r == 2) {
            int percent = random.nextInt(90);
            coupon = new Coupon(DataManager.getNewId(), new ArrayList<>());
            coupon.setDiscountPercent(percent);
            coupon.setMaximumDiscount(1000);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("کد تحفیف");
            alertDialog.setMessage("تبریک! به شما کد تخفیفی با " + percent + " درصد تخفیف تعلق گرفت!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }
    }

    // TODO: Select seller when adding to the cart in each product page...?

    public void finishTapped(View view) {
        customer.decreaseCredit((int) priceAfterDiscount);
        coupon.decrementRemainingUsagesCountForAccount(DataManager.shared().getLoggedInAccount());
        PurchaseLog purchaseLog = new PurchaseLog(DataManager.getNewId(), LocalDateTime.now(), (int) totalPrice,
                (int) (totalPrice - priceAfterDiscount), products, DeliveryStatus.ORDERED, customer);
        DataManager.shared().addLog(purchaseLog);
        int countOfSellLogsAdded = 0;
        for (Map.Entry<Product, Integer> pair : products.entrySet()) {
            if (pair.getKey().getNumberAvailable() > 0) {
                pair.getKey().decrementNumberAvailable();
                HashMap<Product, Integer> productsHashMap = new HashMap<>();
                productsHashMap.put(pair.getKey(), 1);
                for (int i = 0; i < pair.getValue(); i++) {
                    SellLog sellLog = new SellLog(LocalDateTime.now(), productsHashMap, DataManager.getNewId(),
                            pair.getKey().getPrice(), pair.getKey().getCurrentSeller(),
                            (int) ((totalPrice - priceAfterDiscount) / (double) products.size()), DeliveryStatus.ORDERED);
                    countOfSellLogsAdded += 1;
                    DataManager.shared().addLog(sellLog);
                }
                pair.getKey().getCurrentSeller().increaseCredit((int) priceAfterDiscount);
            }
        }
        ((Customer) DataManager.shared().getLoggedInAccount()).emptyCart();
        DataManager.saveData();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("پرداخت با موفقیت انجام شد");
        StringBuilder message = new StringBuilder("با تشکر از خرید شما");
        message.append("\n").append("اطلاعات خرید:").append("\n").append("تاریخ و زمان خرید: ")
                .append(LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE))
                .append("\n").append("تعداد گزارشات فروش ثبت شده: ").append(countOfSellLogsAdded)
                .append("\n").append("قیمت: ").append((int) (totalPrice - priceAfterDiscount));
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
        // TODO: Return to main menu...?
    }
}