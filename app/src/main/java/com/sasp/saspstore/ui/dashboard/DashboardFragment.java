package com.sasp.saspstore.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sasp.saspstore.CategoriesActivity;
import com.sasp.saspstore.R;
import com.sasp.saspstore.SalesListActivity;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.UserRole;
import com.sasp.saspstore.ui.login.LoginActivity;

public class DashboardFragment extends Fragment {

    TextView textView;
    Button seeAllCategoriesButton;
    Button seeAllSalesButton;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textView = root.findViewById(R.id.text_dashboard);
        seeAllCategoriesButton = root.findViewById(R.id.seeAllCategoriesButton_dashboard);
        seeAllSalesButton = root.findViewById(R.id.seeAllSalesButton_dashboard);

        populateData();

        setOnClickListeners();
        /*final UserRole[] selectedRole = {UserRole.CUSTOMER};
        final Button openLoginButton = root.findViewById(R.id.openLogin);
        final RadioGroup roleSelectionGroup = root.findViewById(R.id.login_role);
        openLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        roleSelectionGroup.clearCheck();
        roleSelectionGroup.check(R.id.customer_radio);
        roleSelectionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.customer_radio)
                    selectedRole[0] = UserRole.CUSTOMER;
                if (checkedId == R.id.seller_radio)
                    selectedRole[0] = UserRole.SELLER;
                if (checkedId == R.id.admin_radio)
                    selectedRole[0] = UserRole.ADMIN;
            }
        });*/
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