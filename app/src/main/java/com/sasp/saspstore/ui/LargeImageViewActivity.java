package com.sasp.saspstore.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.sasp.saspstore.ImageSaver;
import com.sasp.saspstore.R;

import java.util.Objects;

public class LargeImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image_view);

        PhotoView photoView = findViewById(R.id.photo_view);
        String mode = getIntent().getExtras().getString("mode");
        if (mode == null || mode.equalsIgnoreCase("resId"))
            photoView.setImageResource(
                Objects.requireNonNull(getIntent().getExtras()).getInt("resId"));
        else if (mode.equalsIgnoreCase("bitmap")) {
            Bitmap bitmap = new ImageSaver(this)
                    .setFileName(getIntent().getExtras().getString("bitmap"))
                    .setDirectoryName("images").load();
            photoView.setImageBitmap(bitmap);
        }
    }
}