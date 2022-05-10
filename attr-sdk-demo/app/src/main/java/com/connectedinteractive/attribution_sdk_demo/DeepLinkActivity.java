package com.connectedinteractive.attribution_sdk_demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.connectedinteractive.attribution_sdk_demo.utils.MockLocationUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class DeepLinkActivity extends CallbackActivity {

    public static double latitude;
    public static double longitude;

    private MockLocationUtils locationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (ConnectTrackerInitHelper.shouldUseFeature(MainActivity.MOCK_LOCATION, getIntent())) {
            locationUtils = new MockLocationUtils(this, !ConnectTrackerInitHelper.shouldUseFeature(MainActivity.MOCK_LOCATION_TIMEOUT, getIntent()));
            if (latitude != 0 && longitude != 0) {
                locationUtils.setLocation(latitude, longitude);
                latitude = 0;
                longitude = 0;
            }
        }

        ConnectTrackerInitHelper.initConnectTracker(this, getIntent(), getCallback());
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();

        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();


    }
}
