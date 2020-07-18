package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Cart;
import com.sasp.saspstore.model.CartItem;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.ui.CartRowAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    ListView cardListView;

    ArrayList<CartItem> cartItems;
    Button btnPurchase;

    public void profileTapped(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cardListView = findViewById(R.id.cartListView);
        btnPurchase = findViewById(R.id.cartBtnPurchase);

        Account account = DataManager.shared().getLoggedInAccount();
        btnPurchase.setVisibility(account instanceof Customer ? View.VISIBLE : View.GONE);
        Cart cart = (account instanceof Customer) ? ((Customer) account).getCart() : DataManager.shared().getTemporaryCart();
        cartItems = new ArrayList<>();
        for (Map.Entry<Product, Integer> productIntegerEntry : cart.getProducts().entrySet()) {
            cartItems.add(new CartItem((productIntegerEntry).getKey(), (productIntegerEntry).getValue()));
        }
        cardListView.setAdapter(new CartRowAdapter(cartItems, this));
    }

    public void purchaseTapped(View view) {
        int totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getTotalPrice();
        }
        if (totalPrice == 0) return;
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("خرید");
        alertDialog.setMessage("مجموع هزینه پرداختی شما " + totalPrice + " است. آیا تمایل به پرداخت دارید؟");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "بله", (dialog, which) -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
            dialog.dismiss();
            finish();
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "خیر", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}