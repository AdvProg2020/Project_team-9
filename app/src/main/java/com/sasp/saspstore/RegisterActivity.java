package com.sasp.saspstore;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.controller.Validator;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Seller;
import com.sasp.saspstore.model.SellerRegistrationRequest;
import com.sasp.saspstore.model.UserRole;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;

public class RegisterActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    ImageButton profilePicture;
    EditText txtName;
    EditText txtLastName;
    EditText txtUsername;
    EditText txtPassword;
    EditText txtPhoneNumber;
    EditText txtEmail;
    EditText txtCompanyDetails;
    RadioGroup radioGroup;
    String addAdmin;
    private String profilePicturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        profilePicture = findViewById(R.id.profile_picture_chooser);
        txtName = findViewById(R.id.register_name);
        txtLastName = findViewById(R.id.register_lname);
        txtUsername = findViewById(R.id.register_username);
        txtPassword = findViewById(R.id.register_password);
        txtPhoneNumber = findViewById(R.id.register_phoneNumber);
        txtEmail = findViewById(R.id.register_email);
        txtCompanyDetails = findViewById(R.id.register_companyDetails);
        radioGroup = findViewById(R.id.radioGroup);

        Intent intent = getIntent();

        addAdmin = intent.getStringExtra("addAdmin");
        if (addAdmin != null && addAdmin.equals("true")) {
            txtCompanyDetails.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
        }

        if (getAccountType() == UserRole.SELLER) {
            txtCompanyDetails.setVisibility(View.VISIBLE);
        } else {
            txtCompanyDetails.setVisibility(View.GONE);
        }

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton_seller) {
                    txtCompanyDetails.setVisibility(View.VISIBLE);
                } else {
                    txtCompanyDetails.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                profilePicturePath = cursor.getString(columnIndex);
                cursor.close();

                String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
                    File imageFile = new File(profilePicturePath);
                    Bitmap picture = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    profilePicture.setImageBitmap(picture);
                } else {
                    EasyPermissions.requestPermissions(this, "Access for storage",
                            101, galleryPermissions);
                }


            } catch (Exception e) {
                Toast.makeText(this,
                        "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void registerTapped(View view) {
        UserRole type = getAccountType();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String name = txtName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String email = txtEmail.getText().toString();
        String phone = txtPhoneNumber.getText().toString();
        String companyDetails = txtCompanyDetails.getText().toString();
        if (addAdmin != null && addAdmin.equals("true")) {
            registerCustomerOrAdmin(type, username, password, name, lastName, email, phone);
            return;
        }
        if (checkForAdmin(type)) return;
        if (hasAnyValidationFailed(type, username, password, email, phone, companyDetails)) return;
        registerIfSeller(type, username, password, name,
                lastName, email, phone, companyDetails);
    }

    private void registerCustomerOrAdmin(UserRole type, String username, String password,
                                         String name, String lastName, String email, String phone) {
        if (type == UserRole.CUSTOMER) {
            Customer customer =
                    new Customer(username, password, email,
                            phone, name, lastName, profilePicturePath);
            DataManager.shared().registerAccount(customer);
        } else if (type == UserRole.ADMIN) {
            Administrator administrator =
                    new Administrator(username, password, email,
                            phone, name, lastName, profilePicturePath);
            DataManager.shared().registerAccount(administrator);
        }
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("ثبت نام با موفقیت انجام شد");
        alertDialog.setMessage("می‌توانید به حساب خود از طریق صفحه ورود، وارد شوید.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private boolean registerIfSeller(UserRole type, String username, String password, String name,
                                     String lastName, String email, String phone,
                                     String companyDetails) {
        if (type == UserRole.SELLER) {
            Seller seller =
                    new Seller(username, password, email, phone,
                            name, lastName, companyDetails, profilePicturePath);
            DataManager.shared().registerAccount(seller);
            SellerRegistrationRequest request =
                    new SellerRegistrationRequest(DataManager.getNewId(), seller);
            DataManager.shared().addRequest(request);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ثبت نام با موفقیت انجام شد");
            alertDialog.setMessage("درخواست افزوده شدن فروشنده به مدیر ارسال شد." +
                    " بعد از تایید، می‌توانید از طریق صفحه ورود، به حساب خود وارد شوید.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                    "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean hasAnyValidationFailed(UserRole type, String username,
                                           String password, String email, String phone,
                                           String companyDetails) {
        return checkEmptyUsernameOrPassword(username, password) ||
                checkForRepeatedUsername(username) || checkEmail(email) || checkPhoneNumber(phone)
                || checkSellerCompanyDetails(type, companyDetails);
    }

    private boolean checkEmptyUsernameOrPassword(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("لطفا نام کاربری و رمز عبور خود را وارد نمایید");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                    "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean checkSellerCompanyDetails(UserRole type, String companyDetails) {
        if (type == UserRole.SELLER && companyDetails.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("لطفا اطلاعات شرکت را پر نمایید. پر کردن اطلاعات شرکت برای فروشنگان اجباری است");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean checkForAdmin(UserRole type) {
        if (type == UserRole.ADMIN && DataManager.shared().hasAnyAdminRegistered()) {
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

    private UserRole getAccountType() {
        if (addAdmin != null && addAdmin.equals("true")) return UserRole.ADMIN;
        UserRole type = UserRole.CUSTOMER;
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        if (radioButton.getText().equals("خریدار")) {
            type = UserRole.CUSTOMER;
        } else if (radioButton.getText().equals("فروشنده")) {
            type = UserRole.SELLER;
        } else if (radioButton.getText().equals("مدیر")) {
            type = UserRole.ADMIN;
        }
        return type;
    }
}