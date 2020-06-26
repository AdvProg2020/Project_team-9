package com.sasp.saspstore.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sasp.saspstore.R;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.controller.Validator;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.Seller;

public class EditProfileActivity extends AppCompatActivity {

    TextView txtFirstName;
    TextView txtLastName;
    TextView txtEmail;
    TextView txtPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        txtFirstName = (TextView) findViewById(R.id.editprofile_txtFirstName);
        txtLastName = (TextView) findViewById(R.id.editprofile_txtLastName);
        txtEmail = (TextView) findViewById(R.id.editprofile_txtEmail);
        txtPhoneNumber = (TextView) findViewById(R.id.editprofile_txtPhoneNumber);

        Account account = DataManager.shared().getLoggedInAccount();
        if (account != null) {
            txtFirstName.setText(account.getFirstName());
            txtLastName.setText(account.getLastName());
            txtEmail.setText(account.getEmail());
            txtPhoneNumber.setText(account.getPhoneNumber());
        } else {
            // TODO: Show login activity!
        }
    }

    public void submitTapped(View view) {
        if (!Validator.shared().emailIsValid(txtEmail.getText().toString())) {
            Toast.makeText(this, "آدرس ایمیل را به درستی وارد نمایید", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Validator.shared().phoneNumberIsValid(txtPhoneNumber.getText().toString())) {
            Toast.makeText(this, "شماره تلفن را به درستی وارد نمایید", Toast.LENGTH_SHORT).show();
            return;
        }
        DataManager.shared().getLoggedInAccount().setFirstName(txtFirstName.getText().toString());
        DataManager.shared().getLoggedInAccount().setLastName(txtLastName.getText().toString());
        DataManager.shared().getLoggedInAccount().setEmail(txtEmail.getText().toString());
        DataManager.shared().getLoggedInAccount().setPhoneNumber(txtPhoneNumber.getText().toString());
        finish();
    }
}