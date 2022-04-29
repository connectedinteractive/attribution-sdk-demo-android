package com.connectedinteractive.attribution_sdk_demo.packagetest;

import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.connectedinteractive.attribution_sdk_demo.MainActivity;
import com.connectedinteractive.attribution_sdk_demo.R;
import com.connectedinteractive.connectsdk.ConnectPushNotification;
import com.connectedinteractive.connectsdk.ConnectTracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.initConnectTracker();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initConnectTracker() {
        // IMPORTANT - can't have a tailing slash
        ConnectTracker.init(this);

        ConnectPushNotification push = new ConnectPushNotification(this);
        push.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        push.setSmallIcon(android.R.drawable.ic_menu_send);

        ConnectTracker.setNotification(push);
        ConnectTracker.initPushNotifications("451153412873");
    }
}
