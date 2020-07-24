package com.sasp.saspstore.ui.notifications;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sasp.saspstore.LoginActivity;
import com.sasp.saspstore.ProfileActivity;
import com.sasp.saspstore.R;
import com.sasp.saspstore.RegisterActivity;
import com.sasp.saspstore.Util;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Account;

public class NotificationsFragment extends Fragment {

    TextView textView;
    Button loginButton;
    Button registerButton;
    Button profileButton;
    private NotificationsViewModel notificationsViewModel;
    private MediaPlayer player;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        notificationsViewModel =
//                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        textView = root.findViewById(R.id.text_notifications);
        loginButton = root.findViewById(R.id.mainprofilepage_loginButton);
        registerButton = root.findViewById(R.id.mainprofilepage_registerButton);
        profileButton = root.findViewById(R.id.mainprofilepage_profileButton);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
        populateData();

        setOnClickListeners();
        player = Util.getMediaPlayer(getContext(), R.raw.spring);

        return root;
    }

    private void setOnClickListeners() {
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(DataManager.context, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(DataManager.context, LoginActivity.class);
            startActivity(intent);
        });
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(DataManager.context, ProfileActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    public void onResume() {
        super.onResume();

        populateData();
        player.start();
    }

    private void populateData() {
        DataManager.shared().populateAllAccountsData();
        Account account = DataManager.shared().getLoggedInAccount();
        if (account == null) {
            textView.setText("برای ورود به ناحیه کاربری، باید ابتدا در سامانه ثبت‌نام نمایید");
            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
            profileButton.setVisibility(View.GONE);
        } else {
            textView.setText(account.getFirstName() + " " + account.getLastName() + " گرامی، به سامانه فروش خوش آمدید");
            loginButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
            profileButton.setVisibility(View.VISIBLE);
        }
    }
}