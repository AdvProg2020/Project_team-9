package com.sasp.saspstore;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Util {
    public static void setBottomNavBar(AppCompatActivity activity) {
        BottomNavigationView navView = activity.findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(activity, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
