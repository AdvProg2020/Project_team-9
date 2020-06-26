package com.sasp.saspstore.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.potyvideo.library.AndExoPlayerView;
import com.sasp.saspstore.R;

public class VideoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        AndExoPlayerView playerView = findViewById(R.id.andExoPlayerView);
        playerView.setSource("https://www.youtube.com/watch?v=G7iI7YmIr30");
    }
}
