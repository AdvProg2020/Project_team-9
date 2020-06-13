package com.sasp.saspstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Seller;
import com.sasp.saspstore.ui.home.EditProfileActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtUsernameAndRole;
    TextView txtEmail;
    TextView txtPhoneNumber;
    TextView txtCredit;
    ViewGroup vgSeeAllCoupons;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = (TextView) findViewById(R.id.fragment_profile_name);
        txtUsernameAndRole = (TextView) findViewById(R.id.fragment_profile_usernameAndRole);
        txtEmail = (TextView) findViewById(R.id.fragment_profile_email);
        txtPhoneNumber = (TextView) findViewById(R.id.fragment_profile_phoneNumber);
        txtCredit = (TextView) findViewById(R.id.fragment_profile_credit);
        vgSeeAllCoupons = (ViewGroup) findViewById(R.id.profile_coupons_root);

        Account account = DataManager.shared().getLoggedInAccount();
        vgSeeAllCoupons.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        if (account != null) {
            txtName.setText(account.getFirstName() + " " + account.getLastName());
            String role = "";
            if (account instanceof Customer) {
                role = "customer";
            } else if (account instanceof Seller) {
                role = "seller";
            } else if (account instanceof Administrator) {
                role = "administrator";
            }
            txtUsernameAndRole.setText(account.getUsername() + " - " + role);
            txtEmail.setText(account.getEmail());
            txtPhoneNumber.setText(account.getPhoneNumber());
            txtCredit.setText("اعتبار: " + account.getCredit());
        } else {
            // TODO: Show login activity!
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profilemenu_btnEdit) {
            startActivity(new Intent(this, EditProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logoutTapped(View view) {
        // TODO: Here!
    }

    public void logTapped(View view) {
        startActivity(new Intent(this, LogActivity.class));
    }

    public void seeAllCouponsTapped(View view) {
        startActivity(new Intent(this, AllCouponsActivity.class));
    }
}