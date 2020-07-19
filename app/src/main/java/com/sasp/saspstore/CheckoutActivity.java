package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
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
    Button finishBankButton;
    Button checkCouponButton;

    Coupon coupon;
    Customer customer;

    double priceAfterDiscount;
    double totalPrice;
    HashMap<Product, Integer> products;

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        txtPhoneNumber = findViewById(R.id.checkout_txtPhoneNumber);
        txtAddress = findViewById(R.id.checkout_txtAddress);
        txtTotalPrice = findViewById(R.id.checkout_totalPrice);
        txtCoupon = findViewById(R.id.checkout_txtCoupon);
        finishButton = findViewById(R.id.checkout_finishButton);
        finishBankButton = findViewById(R.id.checkout_finishBankButton);
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
            txtTotalPriceText.append("چون بیش از ۱۰۰۰ تومان خرج کرده اید، یک تخفیف ۱۰۰ تومانی برای شما در نظر گرفته شده است!!").append("\n");
        }
        txtTotalPriceText.append("میزان تخفیف: ").append(totalPrice - priceAfterDiscount).append("\n");
        txtTotalPriceText.append("مبلغ قابل پرداخت: ").append(priceAfterDiscount);
        if (customer.getCredit() < priceAfterDiscount) {
            txtTotalPriceText.append("\n").append("شما ").append(customer.getCredit()).append(" تومان در حساب خود دارید و متاسفانه نمی‌توانید هزینه این کالاها را از کیف پول خود پرداخت کنید.");
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
        Account account = DataManager.shared().getLoggedInAccount();
        if (coupon != null && account instanceof Customer && coupon.getRemainingUsagesCount() != null) {
            int number = 0;
            if (coupon.getRemainingUsagesCount().containsKey(customer.getUsername()))
                number = coupon.getRemainingUsagesCount().get(customer.getUsername()) - 1;
            coupon.getRemainingUsagesCount().put(customer.getUsername(), Math.max(number, 0));
            DataManager.shared().syncCoupons();
        }
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
//        if (r == 2) {
        int percent = random.nextInt(90);
        coupon = new Coupon(DataManager.getNewId(), new ArrayList<>());
        coupon.setDiscountPercent(percent);
        coupon.setMaximumDiscount(1000);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("کد تحفیف");
        alertDialog.setMessage("تبریک! به شما کد تخفیفی با " + percent + " درصد تخفیف تعلق گرفت!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
//        }
    }

    // TODO: Select seller when adding to the cart in each product page...?

    // TODO: All Changes to this activity should also be done while receiving auction prizes...

    // TODO: No Log or... while auctioning and receiving the prize...

    public void finishEverything() {
        customer.setAddress(txtAddress.getText().toString());
        coupon.decrementRemainingUsagesCountForAccount(DataManager.shared().getLoggedInAccount());
        PurchaseLog purchaseLog = new PurchaseLog(DataManager.getNewId(), LocalDateTime.now(), (int) totalPrice,
                (int) (totalPrice - priceAfterDiscount), products, DeliveryStatus.WAITING, customer);
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
                            (int) ((totalPrice - priceAfterDiscount) / (double) products.size()), DeliveryStatus.WAITING);
                    countOfSellLogsAdded += 1;
                    DataManager.shared().addLog(sellLog);
                }
                pair.getKey().getCurrentSeller().increaseCredit((int) priceAfterDiscount);
            }
        }
        ((Customer) DataManager.shared().getLoggedInAccount()).emptyCart();
        DataManager.shared().syncLogs();
        DataManager.shared().syncCartForUser();
        DataManager.shared().syncCustomers();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("پرداخت با موفقیت انجام شد");
        StringBuilder message = new StringBuilder("با تشکر از خرید شما");
        message.append("\n").append("اطلاعات خرید:").append("\n").append("تاریخ و زمان خرید: ")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .append("\n").append("تعداد گزارشات فروش ثبت شده: ").append(countOfSellLogsAdded).append("\n");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        alertDialog.show();
    }

    public void payByCreditTapped(View view) {
        customer.decreaseCredit((int) priceAfterDiscount);
        finishEverything();
    }

    public void payByBankTapped(View view) {
        Account account = DataManager.shared().getLoggedInAccount();
        if (!(account instanceof Customer)) return;
        Customer customer = (Customer) account;
        BankAPI.tellBankAndReceiveResponse("get_token " + customer.getUsername() + " " + customer.getPassword(), token ->
                BankAPI.tellBankAndReceiveResponse("create_receipt " + token + " " +
                        "move" + " " + (int) priceAfterDiscount + " " + customer.getBankAccountNumber() +
                        " " + DataManager.shared().getAdminBankAccountNumber() + " pBBT", receiptID ->
                        BankAPI.tellBankAndReceiveResponse("pay " + receiptID, response ->
                                runOnUiThread(() -> {
                                    switch (response) {
                                        case "source account does not have enough money":
                                            Toast.makeText(CheckoutActivity.this, "حساب مبدا به اندازه کافی پول ندارد", Toast.LENGTH_LONG).show();
                                            break;
                                        case "done successfully":
                                            finishEverything();
                                            break;
                                    }
                                }))));
    }
}