package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Category;
import com.sasp.saspstore.model.Coupon;
import com.sasp.saspstore.model.Product;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    ListView listView;


    ArrayList<Category> allCategories = DataManager.shared().getAllCategories();
    Category parentCategory = null;

    ArrayAdapter<Category> adapter;

    // TODO: No place for Category description in the list!!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        listView = findViewById(R.id.allCategoriesList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, allCategories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String previousParentCategoryID = (parentCategory == null) ? "" : parentCategory.getId();
            parentCategory = (Category) listView.getItemAtPosition(position);
            repopulateAllCategories();
            if (allCategories.isEmpty()) {
                Intent intent = new Intent(this, ProductListActivity.class);
                StringBuilder productIDs = new StringBuilder();
                ArrayList<Product> allProducts = DataManager.shared().getAllProducts();
                for (int i = 0, allProductsSize = allProducts.size(); i < allProductsSize; i++) {
                    Product product = allProducts.get(i);
                    if (product.getCategory().getId().equals(parentCategory.getId()))
                        productIDs.append(product.getProductId());
                    if (i != allProductsSize - 1) productIDs.append(";;;;");
                }
                intent.putExtra("openOrSelect", "open");
                intent.putExtra("productIDs", productIDs.toString());
                intent.putExtra("categoryID", parentCategory.getId());
                parentCategory = DataManager.shared().getCategoryWithId(previousParentCategoryID);
                repopulateAllCategories();
                startActivity(intent);
            } else adapter.notifyDataSetChanged();
        });
        Account account = DataManager.shared().getLoggedInAccount();
        // TODO: Does the next listener work correctly??
        if (account instanceof Administrator) listView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.coupon_editing_dialog, null);
            dialogBuilder.setView(dialogView);

            Category category = (Category) listView.getItemAtPosition(position);

            final TextView firstTextView = (TextView) dialogView.findViewById(R.id.couponAlertTextView);
            final TextView secondTextView = (TextView) dialogView.findViewById(R.id.couponAlertProductsTextView);

            firstTextView.setText("لطفا نام و توضیحات مورد نظر برای دسته‌بندی را وارد نمایید");
            secondTextView.setText("");

            final EditText firstEditText = (EditText) dialogView.findViewById(R.id.editDiscountPercent);
            final EditText secondEditText = (EditText) dialogView.findViewById(R.id.editMaximumDiscount);
            firstEditText.setText(category.getName());
            secondEditText.setText(category.getDescription());
            dialogBuilder.setTitle("ویرایش اطلاعات دسته‌بندی").setMessage("").setPositiveButton("ثبت تغییرات", (dialog, whichButton) -> {
                String name = firstEditText.getText().toString();
                String description = secondEditText.getText().toString();
                category.setName(name);
                category.setDescription(description);
                DataManager.saveData();
                adapter.notifyDataSetChanged();
            }).setNegativeButton("حذف دسته‌بندی", (dialog, whichButton) -> {
                DataManager.shared().removeCategory((Category) listView.getItemAtPosition(position), parentCategory);
                adapter.notifyDataSetChanged();
            }).setNeutralButton("بازگشت", null);
            AlertDialog b = dialogBuilder.create();
            b.show();
            return true;
        });

        FloatingActionButton fab = findViewById(R.id.categories_fab);
        fab.setVisibility(account instanceof Administrator ? View.VISIBLE : View.GONE);
        fab.setImageBitmap(textAsBitmap("+", 40, Color.WHITE));
        if (account instanceof Administrator) fab.setOnClickListener(view -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.coupon_editing_dialog, null);
            dialogBuilder.setView(dialogView);
            final TextView firstTextView = (TextView) dialogView.findViewById(R.id.couponAlertTextView);
            final TextView secondTextView = (TextView) dialogView.findViewById(R.id.couponAlertProductsTextView);
            firstTextView.setText("لطفا نام و توضیحات مورد نظر برای دسته‌بندی را وارد نمایید");
            secondTextView.setText("");
            final EditText firstEditText = (EditText) dialogView.findViewById(R.id.editDiscountPercent);
            final EditText secondEditText = (EditText) dialogView.findViewById(R.id.editMaximumDiscount);
            dialogBuilder.setTitle("افزودن دسته‌بندی").setMessage("").setPositiveButton("ثبت دسته‌بندی", (dialog, whichButton) -> {
                String name = firstEditText.getText().toString();
                String description = secondEditText.getText().toString();
                // TODO: we are providing empty array for unique features... this is important...!
                Category category = new Category(DataManager.getNewId(), name, description, (parentCategory == null) ? "" : parentCategory.getId(),
                        new ArrayList<>());
                DataManager.shared().addCategory(category);
                adapter.notifyDataSetChanged();
            }).setNeutralButton("بازگشت", null);
            AlertDialog b = dialogBuilder.create();
            b.show();
        });
    }

    private Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    private void repopulateAllCategories() {
        if (parentCategory == null) allCategories = DataManager.shared().getAllCategories();
        else {
            allCategories = new ArrayList<>();
            for (Category category : DataManager.shared().getAllCategories())
                if (category.getParentCategory() != null && category.getParentCategory().getId().equals(parentCategory.getId()))
                    allCategories.add(category);
        }
    }
}