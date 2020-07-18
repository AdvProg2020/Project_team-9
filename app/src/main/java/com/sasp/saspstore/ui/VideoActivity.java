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
        playerView.setSource("https://as9.cdn.asset.aparat.com/aparat-video/647f9dc076f821bcc6bc893ac5bd12ef16866763-144p.mp4");
    }
}
