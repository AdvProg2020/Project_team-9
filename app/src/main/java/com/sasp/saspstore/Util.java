package com.sasp.saspstore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sasp.saspstore.ui.LargeImageViewActivity;

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

    public static void showLargePhoto(Context context, int resId) {
        Intent intent = new Intent(context, LargeImageViewActivity.class);
        intent.putExtra("resId", resId);
        context.startActivity(intent);
    }

    public static void showLargePhoto(Context context, Bitmap bitmap) {
        Intent intent = new Intent(context, LargeImageViewActivity.class);
        intent.putExtra("bitmap", bitmap);
        intent.putExtra("mode", "bitmap");
        context.startActivity(intent);
    }

    public static MediaPlayer getMediaPlayer(Context context, int resId) {
        final MediaPlayer[] player = new MediaPlayer[1];
        new Thread() {
            @Override
            public void run() {
                player[0] = MediaPlayer.create(context, resId);
                player[0].setLooping(true);
                player[0].setVolume(100, 100);
            }
        }.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return player[0];
    }
}
