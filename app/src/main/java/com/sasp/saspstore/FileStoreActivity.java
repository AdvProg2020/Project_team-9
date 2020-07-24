package com.sasp.saspstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sasp.saspstore.ui.filestore.FileStoreFragment;

public class FileStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_store_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FileStoreFragment.newInstance())
                    .commitNow();
        }
    }
}