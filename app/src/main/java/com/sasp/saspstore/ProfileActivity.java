package com.sasp.saspstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    TextView txtCompanyDetails;
    Button seeAllCouponsButton;
    Button seeAllUsersButton;
    Button seeAllCategoriesButton;
    Button seeAllRequestsButton;
    Button seeCartButton;

    // TODO: Add another administrator is not implemented yet... waiting for login page

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
        txtCompanyDetails = (TextView) findViewById(R.id.fragment_profile_companyDetails);
        seeAllCouponsButton =  findViewById(R.id.seeAllCouponsButton);
        seeAllUsersButton = findViewById(R.id.seeAllUsersButton);
        seeAllCategoriesButton = findViewById(R.id.seeAllCategoriesButton);
        seeCartButton = findViewById(R.id.seeCartButton);
        seeAllRequestsButton = findViewById(R.id.seeAllRequestsButton);

        populateData();
    }

    private void populateData() {
        Account account = DataManager.shared().getLoggedInAccount();
        seeAllCouponsButton.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        seeAllUsersButton.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        seeAllCategoriesButton.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        seeAllRequestsButton.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        seeCartButton.setVisibility(account instanceof Customer ? View.VISIBLE : View.GONE);
        txtCredit.setVisibility((account instanceof Customer) || (account instanceof Seller) ? View.VISIBLE : View.GONE);
        txtCompanyDetails.setVisibility(account instanceof Seller ? View.VISIBLE : View.GONE);
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
    protected void onResume() {
        super.onResume();
        populateData();
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
        DataManager.shared().logout();
        finish();
    }

    public void logTapped(View view) {
        startActivity(new Intent(this, LogActivity.class));
    }

    public void seeAllCouponsTapped(View view) {
        startActivity(new Intent(this, AllCouponsActivity.class));
    }

    public void seeAllUsersTapped(View view) {
        Intent intent = new Intent(this, UsersListActivity.class);
        intent.putExtra("sender", "administratorProfile");
        startActivity(intent);
    }

    public void seeAllCategoriesTapped(View view) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    public void seeAllRequestsTapped(View view) {
        Intent intent = new Intent(this, AdministratorRequestsActivity.class);
        startActivity(intent);
    }

    public void seeCartTapped(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}