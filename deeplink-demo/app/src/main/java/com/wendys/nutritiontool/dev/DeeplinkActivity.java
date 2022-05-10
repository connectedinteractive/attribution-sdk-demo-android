package com.wendys.nutritiontool.dev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.wendys.nutritiontool.dev.databinding.ActivityDeeplinkBinding;

public class DeeplinkActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDeeplinkBinding binding;
    private TextView deeplinkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDeeplinkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        deeplinkTextView = (TextView) findViewById(R.id.deeplink_textview);

        Intent intent = getIntent();
        Uri url = intent.getData();

        String host = url.getHost();
        String path = url.getPath();
        String query = url.getQuery();

        String deeplinkData = "Host: " + host + "\nPath: " + path + "\nQuery: " + query;
        deeplinkTextView.setText(deeplinkData);

    }
}