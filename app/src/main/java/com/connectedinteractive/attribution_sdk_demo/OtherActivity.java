package com.connectedinteractive.attribution_sdk_demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.connectedinteractive.connectsdk.ConnectTracker;

public class OtherActivity extends AppCompatActivity {

    public static final String OTHER_ACTIVITY_EVENT_NAME = "other_activity_event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        ConnectTracker.resolveDeeplink(appLinkData.toString(), new String[] { "connected_interactive_showcase", "connectedinteractive.com"});
        ConnectTracker.appWillOpenUrl(appLinkData);
    }

    public void onClickTrackEvent(View v) {
        ConnectTracker.trackEvent(OTHER_ACTIVITY_EVENT_NAME);
    }
}
