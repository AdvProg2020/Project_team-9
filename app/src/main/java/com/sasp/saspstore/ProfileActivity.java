package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Seller;

public class ProfileActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtUsernameAndRole;
    TextView txtEmail;
    TextView txtPhoneNumber;
    TextView txtCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = (TextView) findViewById(R.id.fragment_profile_name);
        txtUsernameAndRole = (TextView) findViewById(R.id.fragment_profile_usernameAndRole);
        txtEmail = (TextView) findViewById(R.id.fragment_profile_email);
        txtPhoneNumber = (TextView) findViewById(R.id.fragment_profile_phoneNumber);
        txtCredit = (TextView) findViewById(R.id.fragment_profile_credit);

        Account account = DataManager.shared().getLoggedInAccount();
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
            txtCredit.setText(account.getCredit());
        } else {
            // TODO: Show login activity!
        }
    }
}