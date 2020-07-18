package com.sasp.saspstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Ad;
import com.sasp.saspstore.model.AddAdBySellerRequest;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.SellLog;
import com.sasp.saspstore.model.Seller;
import com.sasp.saspstore.ui.home.EditProfileActivity;

import java.io.File;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    EditText adTxt;
    EditText creditTxt;
    Button adBtn;
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
    Button addCreditButton;
    Button removeCreditButton;
    Button inSellProductListButton;
    ImageView proPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        adTxt = findViewById(R.id.profile_adTxt);
        adBtn = findViewById(R.id.profile_adBtn);
        txtName = (TextView) findViewById(R.id.fragment_profile_name);
        txtUsernameAndRole = (TextView) findViewById(R.id.fragment_profile_usernameAndRole);
        txtEmail = (TextView) findViewById(R.id.fragment_profile_email);
        txtPhoneNumber = (TextView) findViewById(R.id.fragment_profile_phoneNumber);
        txtCredit = (TextView) findViewById(R.id.fragment_profile_credit);
        txtCompanyDetails = findViewById(R.id.fragment_profile_companyDetails);
        seeAllCouponsButton = findViewById(R.id.seeAllCouponsButton);
        seeAllUsersButton = findViewById(R.id.seeAllUsersButton);
        seeAllCategoriesButton = findViewById(R.id.seeAllCategoriesButton);
        seeCartButton = findViewById(R.id.seeCartButton);
        seeAllRequestsButton = findViewById(R.id.seeAllRequestsButton);
        addCreditButton = findViewById(R.id.addCreditButton);
        removeCreditButton = findViewById(R.id.removeCreditButton);
        inSellProductListButton = findViewById(R.id.inSellProductListButton);
        creditTxt = findViewById(R.id.profile_creditTxt);

        populateData();
    }

    private void updateTxtCreditText() {
        Account account = DataManager.shared().getLoggedInAccount();
        txtCredit.setText("اعتبار: " + account.getCredit());
    }

    private void populateData() {
        DataManager.shared().populateData();
        Account account = DataManager.shared().getLoggedInAccount();
        adBtn.setVisibility(account instanceof Seller ? View.VISIBLE : View.GONE);
        adTxt.setVisibility(account instanceof Seller ? View.VISIBLE : View.GONE);
        seeAllCouponsButton.setVisibility(account instanceof Seller ? View.GONE : View.VISIBLE);
        seeAllUsersButton.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        seeAllCategoriesButton.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        seeAllRequestsButton.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        seeCartButton.setVisibility(account instanceof Customer ? View.VISIBLE : View.GONE);
        creditTxt.setVisibility(account instanceof Customer ? View.VISIBLE : View.GONE);
        addCreditButton.setVisibility((account instanceof Customer || account instanceof Seller) ? View.VISIBLE : View.GONE);
        removeCreditButton.setVisibility(account instanceof Seller ? View.VISIBLE : View.GONE);
        txtCredit.setVisibility((account instanceof Customer) || (account instanceof Seller) ? View.VISIBLE : View.GONE);
        txtCompanyDetails.setVisibility(account instanceof Seller ? View.VISIBLE : View.GONE);
        inSellProductListButton.setVisibility(account instanceof Seller ? View.VISIBLE : View.GONE);
        if (account != null) {
            txtName.setText(account.getFirstName() + " " + account.getLastName());
            try {
                File imageFile = new File(account.getProfilePicPath());
                proPic.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
            } catch (Exception e) {

            }
            String role = "";
            if (account instanceof Customer) {
                role = "customer";
            } else if (account instanceof Seller) {
                role = "seller";
            } else if (account instanceof Administrator) {
                role = "administrator";
            }
            if (account instanceof Seller)
                txtCompanyDetails.setText(((Seller) account).getCompanyDetails());
            txtUsernameAndRole.setText(account.getUsername() + " - " + role);
            txtEmail.setText(account.getEmail());
            txtPhoneNumber.setText(account.getPhoneNumber());
            updateTxtCreditText();
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
        Account account = DataManager.shared().getLoggedInAccount();
        Intent intent = new Intent(this, AllCouponsActivity.class);
        if (account instanceof Customer) intent.putExtra("customerUsername", account.getUsername());
        startActivity(intent);
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

//    public void addCreditTapped(View view) {
//        Account account = DataManager.shared().getLoggedInAccount();
//        if (account instanceof Customer) {
//            account.increaseCredit(5);
//            Toast.makeText(this, "اعتبار حساب شما ۵ تومان افزایش یافت", Toast.LENGTH_SHORT).show();
//            txtCredit.setText("اعتبار: " + account.getCredit());
//        }
//    }

    public boolean doesProductContainSeller(Product product, Seller seller) {
        for (Seller productSeller : product.getSellers()) {
            if (productSeller.getUsername().equals(seller.getUsername())) return true;
        }
        return false;
    }

    public void inSellProductListTapped(View view) {
        ArrayList<String> productIDs = findAppropriateProductIDs();
        if (productIDs == null) return;
        StringBuilder productIDsStringBuilder = getStringBuilderFromProductIDs(productIDs);
        navigateToProductsListActivityToShowSellerProducts(productIDsStringBuilder);
    }

    private void navigateToProductsListActivityToShowSellerProducts(StringBuilder productIDsStringBuilder) {
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra("openOrSelect", "open");
        intent.putExtra("productIDs", productIDsStringBuilder.toString());
        startActivity(intent);
    }

    private ArrayList<String> findAppropriateProductIDs() {
        Account account = DataManager.shared().getLoggedInAccount();
        if (!(account instanceof Seller)) return null;
        ArrayList<String> productIDs = new ArrayList<>();
        for (Product product : DataManager.shared().getAllProducts()) {
            if (doesProductContainSeller(product, (Seller) account)) {
                productIDs.add(product.getProductId());
            }
        }
        return productIDs;
    }

    private StringBuilder getStringBuilderFromProductIDs(ArrayList<String> productIDs) {
        StringBuilder productIDsStringBuilder = new StringBuilder();
        for (int i = 0, productIDsSize = productIDs.size(); i < productIDsSize; i++) {
            String productID = productIDs.get(i);
            productIDsStringBuilder.append(productID);
            if (i != productIDsSize - 1) productIDsStringBuilder.append(";;;;");
        }
        return productIDsStringBuilder;
    }

    public void addAdTapped(View view) {
        String ad = adTxt.getText().toString();
        Account account = DataManager.shared().getLoggedInAccount();
        if (!ad.equals("") && account instanceof Seller) {
            if (account.getCredit() < 5) {
                Toast.makeText(this, "اعتبار حساب شما ناکافی است", Toast.LENGTH_SHORT).show();
                return;
            }
            account.decreaseCredit(5);
            Ad adObject = new Ad(DataManager.getNewId(), ad);
            AddAdBySellerRequest request = new AddAdBySellerRequest(DataManager.getNewId(), (Seller) account, adObject);
            DataManager.shared().addRequest(request);
            Toast.makeText(this, "درخواست افزودن تبلیغ برای مدیر ارسال شد", Toast.LENGTH_SHORT).show();
            populateData();
        }
    }

    // TODO: Add and remove credit not tested... :)
    // TODO: KarMozd and Hadeaqale mojood dar kife pul

    public void addCreditTapped(View view) {
        Account account = DataManager.shared().getLoggedInAccount();
        BankAPI.tellBankAndReceiveResponse("get_token " + account.getUsername() + " " + account.getPassword(), token -> {
            BankAPI.tellBankAndReceiveResponse("create_receipt " + token + " " +
                    "withdraw" + " " + Integer.parseInt(creditTxt.getText().toString()) + " " +
                    account.getBankAccountNumber() + " " + "-1" + " withdraw", receiptID ->
                    BankAPI.tellBankAndReceiveResponse("pay " + receiptID, response ->
                            runOnUiThread(() -> {
                                switch (response) {
                                    case "source account does not have enough money":
                                        Toast.makeText(ProfileActivity.this, "حساب مبدا به اندازه کافی پول ندارد", Toast.LENGTH_LONG).show();
                                        break;
                                    case "done successfully":
                                        Toast.makeText(ProfileActivity.this, "عملیات با موفقیت انجام شد", Toast.LENGTH_LONG).show();
                                        account.increaseCredit(Integer.parseInt(creditTxt.getText().toString()));
                                        updateTxtCreditText();
                                        break;
                                }
                            })));
        });
    }

    public void removeCreditTapped(View view) {
        Account account = DataManager.shared().getLoggedInAccount();
        if (Integer.parseInt(creditTxt.getText().toString()) > account.getCredit()) {
            Toast.makeText(this, "اعتبار حساب شما ناکافی است", Toast.LENGTH_LONG).show();
            return;
        }
        BankAPI.tellBankAndReceiveResponse("get_token " + account.getUsername() + " " + account.getPassword(), token -> {
            BankAPI.tellBankAndReceiveResponse("create_receipt " + token + " " +
                    "deposit" + " " + Integer.parseInt(creditTxt.getText().toString()) + " " +
                    "-1" + " " + account.getBankAccountNumber() + " deposit", receiptID ->
                    BankAPI.tellBankAndReceiveResponse("pay " + receiptID, response ->
                            runOnUiThread(() -> {
                                Toast.makeText(ProfileActivity.this, "عملیات با موفقیت انجام شد", Toast.LENGTH_LONG).show();
                                account.decreaseCredit(Integer.parseInt(creditTxt.getText().toString()));
                                updateTxtCreditText();
                            })));
        });
    }
}