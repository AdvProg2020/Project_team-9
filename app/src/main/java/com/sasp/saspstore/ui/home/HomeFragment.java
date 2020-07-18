package com.sasp.saspstore.ui.home;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sasp.saspstore.R;
import com.sasp.saspstore.Util;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Ad;
import com.sasp.saspstore.ui.LargeImageViewActivity;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    CarouselView carouselView;
    ArrayAdapter<Ad> adapter;
    private HomeViewModel homeViewModel;
    private MediaPlayer player;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        int[] slides = {R.drawable.image_asset1, R.drawable.image_asset2, R.drawable.image_asset3};
        carouselView = root.findViewById(R.id.carouselView);
        carouselView.setPageCount(slides.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(slides[position]);
            }
        });
        carouselView.setImageClickListener(position -> {
            Intent intent = new Intent(getContext(), LargeImageViewActivity.class);
            intent.putExtra("resId", slides[position]);
            getContext().startActivity(intent);
        });

        ImageView gifView = root.findViewById(R.id.gif_view);
        Glide.with(this).load(R.drawable.welcome_gif).into(gifView);

        ListView adsListView = root.findViewById(R.id.list_ads);
        ArrayList<Ad> allAds = DataManager.shared().getAllAds();
        allAds.removeIf(Objects::isNull);
        DataManager.shared().setAllAds(allAds);
        if (allAds.size() == 0) {
            allAds = new ArrayList<>();
            DataManager.shared().setAllAds(allAds);
            allAds.add(new Ad(DataManager.getNewId(), "موردی برای نمایش وجود ندارد"));
        }
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, allAds);
        adsListView.setAdapter(adapter);
        player = Util.getMediaPlayer(getContext(), R.raw.swanlake);
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) adapter.notifyDataSetChanged();
        if (player != null) player.start();
    }
}