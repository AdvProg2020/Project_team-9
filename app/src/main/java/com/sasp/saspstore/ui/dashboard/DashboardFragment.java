package com.sasp.saspstore.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sasp.saspstore.CategoriesActivity;
import com.sasp.saspstore.LoginActivity;
import com.sasp.saspstore.ProfileActivity;
import com.sasp.saspstore.R;
import com.sasp.saspstore.RegisterActivity;
import com.sasp.saspstore.SalesListActivity;
import com.sasp.saspstore.controller.DataManager;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    TextView textView;
    Button seeAllCategoriesButton;
    Button seeAllSalesButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textView = root.findViewById(R.id.text_dashboard);
        seeAllCategoriesButton = root.findViewById(R.id.seeAllCategoriesButton_dashboard);
        seeAllSalesButton = root.findViewById(R.id.seeAllSalesButton_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        populateData();

        setOnClickListeners();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }

    private void populateData() {
        textView.setText("آیا می‌دانستید " + DataManager.shared().getAllProducts().size()
                + " محصول در این فروشگاه موجود است؟");
    }


    private void setOnClickListeners() {
        seeAllCategoriesButton.setOnClickListener(view -> {
            Intent intent = new Intent(DataManager.context, CategoriesActivity.class);
            startActivity(intent);
        });

        seeAllSalesButton.setOnClickListener(view -> {
            Intent intent = new Intent(DataManager.context, SalesListActivity.class);
            startActivity(intent);
        });
    }
}