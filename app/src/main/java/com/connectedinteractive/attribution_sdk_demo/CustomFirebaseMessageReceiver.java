package com.connectedinteractive.attribution_sdk_demo;

import android.util.Log;

import com.connectedinteractive.connectsdk.ConnectTrackerNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomFirebaseMessageReceiver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("[ConnectSdk]", "custom receiver received custom message");

        ConnectTrackerNotification.onMessageReceived(this, remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("[ConnectSdk]", "FCM - custom receiver firebase id: " + token);

        ConnectTrackerNotification.onNewTokenRegistered(token, this);
    }
}
