package com.sasp.saspstore.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.sasp.saspstore.MainActivity;
import com.sasp.saspstore.R;
import com.sasp.saspstore.Util;
import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Ad;
import com.sasp.saspstore.ui.LargeImageViewActivity;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    CarouselView carouselView;
    ArrayAdapter<Ad> adapter;

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
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Util.showLargePhoto(getContext(), slides[position]);
            }
        });

        ImageView gifView = root.findViewById(R.id.gif_view);
        Glide.with(this).load(R.drawable.welcome_gif).into(gifView);

        ListView adsListView = root.findViewById(R.id.list_ads);
        ArrayList<Ad> allAds = DataManager.shared().getAllAds();
        allAds.removeIf(Objects::isNull);
        if (allAds.size() == 0) {
            allAds = new ArrayList<>();
            allAds.add(new Ad(DataManager.getNewId(), "موردی برای نمایش وجود ندارد"));
        }
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, allAds);
        adsListView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}