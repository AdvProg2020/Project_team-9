package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Category;

import java.util.ArrayList;

public class AddCategoryActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtDescription;
    EditText txtFeatureOne;
    EditText txtFeatureTwo;
    EditText txtParentCategoryName;
    String parentCategoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        txtName = findViewById(R.id.addCategory_txtName);
        txtDescription = findViewById(R.id.addCategory_txtDescription);
        txtFeatureOne = findViewById(R.id.addCategory_txtFeatureOne);
        txtFeatureTwo = findViewById(R.id.addCategory_txtFeatureTwo);
        txtParentCategoryName = findViewById(R.id.addCategory_txtParentCategoryName);

        Intent intent = getIntent();
        parentCategoryID = intent.getStringExtra("parentCategoryID");
        if (parentCategoryID == null) parentCategoryID = "";
        else {
            Category category = DataManager.shared().getCategoryWithId(parentCategoryID);
            if (category != null) txtParentCategoryName.setText(category.getName());
        }

    }

    public void addCategoryTapped(View view) {
        // TODO: Error if name or desc or... is empty??
        ArrayList<String> features = new ArrayList<>();
        features.add(txtFeatureOne.getText().toString());
        features.add(txtFeatureTwo.getText().toString());
        Category parent = DataManager.shared().getCategoryWithName(txtParentCategoryName.getText().toString());
        Category category = new Category(DataManager.getNewId(), txtName.getText().toString(),
                txtDescription.getText().toString(), parent == null ? parentCategoryID : parent.getId(), features);
        DataManager.shared().addCategory(category);
        finish();
    }
}