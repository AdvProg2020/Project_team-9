package com.sasp.saspstore;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Seller;

import java.io.IOException;
import java.util.HashMap;

// b5d985b9114243fd93a73d871816d728
// a4cb354a9400493b9421d6fce28bcaec

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.login_username);
        txtPassword = findViewById(R.id.login_password);
    }

    public void registerTapped(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void loginTapped(View view) {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if (!DataManager.shared().doesUserWithGivenUsernameExist(username)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("نام کاربری وارد شده یافت نشد");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return;
        }
        if (!DataManager.shared().givenUsernameHasGivenPassword(username, password)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("رمز عبور وارد شده نادرست است");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return;
        }
        DataManager.AccountType result = DataManager.shared().login(username, password);
        if (result == DataManager.AccountType.NONE) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("لطفا دوباره تلاش نمایید");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return;
        }
        if (result == DataManager.AccountType.CUSTOMER) {
            HashMap<Product, Integer> prods = ((Customer) (DataManager.shared().getLoggedInAccount())).getCart().getProducts();
            prods.putAll(DataManager.shared().getTemporaryCart().getProducts());
            ((Customer) (DataManager.shared().getLoggedInAccount())).getCart().setProducts(prods);
            DataManager.shared().getTemporaryCart().setProducts(new HashMap<>());
            DataManager.shared().syncTemporaryCart();
            DataManager.shared().syncCartForUser();
        } else if (result == DataManager.AccountType.SELLER) {
            Seller seller = (Seller) (DataManager.shared().getLoggedInAccount());
            if (!(seller).isPermittedToSell()) {
                DataManager.shared().logout();
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("خطا");
                alertDialog.setMessage("شما توسط مدیر تایید نشده‌اید");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
                alertDialog.show();
                return;
            }
            try {
                FileServer fileServer = new FileServer(0, seller);
                seller.setHostAddress(fileServer.getInetAddress().getHostAddress());
                seller.setPort(fileServer.getPort());
                fileServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        finish();
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}