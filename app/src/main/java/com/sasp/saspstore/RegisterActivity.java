package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.controller.Validator;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Seller;
import com.sasp.saspstore.model.SellerRegistrationRequest;

public class RegisterActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtLastName;
    EditText txtUsername;
    EditText txtPassword;
    EditText txtPhoneNumber;
    EditText txtEmail;
    EditText txtCompanyDetails;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtName = findViewById(R.id.register_name);
        txtLastName = findViewById(R.id.register_lname);
        txtUsername = findViewById(R.id.register_username);
        txtPassword = findViewById(R.id.register_password);
        txtPhoneNumber = findViewById(R.id.register_phoneNumber);
        txtEmail = findViewById(R.id.register_email);
        txtCompanyDetails = findViewById(R.id.register_companyDetails);
        radioGroup = findViewById(R.id.radioGroup);
    }

    public void registerTapped(View view) {
        String type = getAccountType();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String name = txtName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String email = txtEmail.getText().toString();
        String phone = txtPhoneNumber.getText().toString();
        String companyDetails = txtCompanyDetails.getText().toString();
        if (hasAnyValidationFailed(type, username, password, email, phone, companyDetails)) return;
        if (registerIfSeller(type, username, password, name, lastName, email, phone, companyDetails))
            return;
        registerCustomerOrAdmin(type, username, password, name, lastName, email, phone);
    }

    private void registerCustomerOrAdmin(String type, String username, String password, String name, String lastName, String email, String phone) {
        if (type.equalsIgnoreCase("customer")) {
            Customer customer = new Customer(username, password, email, phone, name, lastName);
            DataManager.shared().registerAccount(customer);
        } else if (type.equalsIgnoreCase("administrator")) {
            Administrator administrator = new Administrator(username, password, email, phone, name, lastName);
            DataManager.shared().registerAccount(administrator);
        }
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("ثبت نام با موفقیت انجام شد");
        alertDialog.setMessage("می‌توانید به حساب خود از طریق صفحه ورود، وارد شوید.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private boolean registerIfSeller(String type, String username, String password, String name, String lastName, String email, String phone, String companyDetails) {
        if (type.equalsIgnoreCase("seller")) {
            Seller seller = new Seller(username, password, email, phone, name, lastName, companyDetails);
            DataManager.shared().registerAccount(seller);
            SellerRegistrationRequest request = new SellerRegistrationRequest(DataManager.getNewId(), seller);
            DataManager.shared().addRequest(request);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ثبت نام با موفقیت انجام شد");
            alertDialog.setMessage("درخواست افزوده شدن فروشنده به مدیر ارسال شد." +
                    " بعد از تایید، می‌توانید از طریق صفحه ورود، به حساب خود وارد شوید.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean hasAnyValidationFailed(String type, String username, String password, String email, String phone, String companyDetails) {
        return checkForAdmin(type) || checkEmptyUsernameOrPassword(username, password) ||
                checkForRepeatedUsername(username) || checkEmail(email) || checkPhoneNumber(phone) ||
                checkSellerCompanyDetails(type, companyDetails);
    }

    private boolean checkEmptyUsernameOrPassword(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("لطفا نام کاربری و رمز عبور خود را وارد نمایید");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean checkSellerCompanyDetails(String type, String companyDetails) {
        if (type.equalsIgnoreCase("seller") && companyDetails.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("لطفا اطلاعات شرکت را پر نمایید. پر کردن اطلاعات شرکت برای فروشنگان اجباری است");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean checkForAdmin(String type) {
        if (type.equalsIgnoreCase("administrator") && DataManager.shared().hasAnyAdminRegistered()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("تنها مدیر می‌تواند مدیران جدید را به سامانه بیفزاید");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        if (!Validator.shared().phoneNumberIsValid(phoneNumber)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("قالب شماره تلفن وارد شده نادرست است");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean checkEmail(String email) {
        if (!Validator.shared().emailIsValid(email)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("قالب ایمیل وارد شده نادرست است");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean checkForRepeatedUsername(String username) {
        if (DataManager.shared().doesUserWithGivenUsernameExist(username)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("نام کاربری وارد شده قبلا استفاده شده است");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return true;
        }
        return false;
    }

    private String getAccountType() {
        String type = "";
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        if (radioButton.getText().equals("خریدار")) {
            type = "customer";
        } else if (radioButton.getText().equals("فروشنده")) {
            type = "seller";
        } else if (radioButton.getText().equals("مدیر")) {
            type = "administrator";
        }
        return type;
    }
}