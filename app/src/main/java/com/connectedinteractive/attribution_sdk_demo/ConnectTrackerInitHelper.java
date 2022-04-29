package com.connectedinteractive.attribution_sdk_demo;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;

import com.connectedinteractive.attribution_sdk_demo.utils.Constants;
import com.connectedinteractive.attribution_sdk_demo.utils.LogUtils;
import com.connectedinteractive.connectsdk.BuildConfig;
import com.connectedinteractive.connectsdk.ConnectPushNotification;
import com.connectedinteractive.connectsdk.ConnectTracker;
import com.connectedinteractive.connectsdk.ConnectTrackerCallback;
import com.connectedinteractive.connectsdk.ConnectTrackerOptions;

public class ConnectTrackerInitHelper {
    public static final String USE_CALLBACKS = "use_callbacks";
    public static final String JUST_CALLBACKS_COUNT = "just_callbacks_count";
    public static final String USE_PUSH_NOTIFICATION_SERVICES = "use_push_notification_services";
    public static final String USE_LOCATION_SERVICES = "use_location_services";

    public static void initConnectTracker(Context context, Intent intent, ConnectTrackerCallback callback) {
        initWithOptions(context, intent, callback);
    }

    private static void initWithOptions(Context context, Intent intent, ConnectTrackerCallback callback) {
        ConnectTrackerOptions connectTrackerOptions = new ConnectTrackerOptions();
        connectTrackerOptions.setAppKey(Constants.APP_KEY);
        connectTrackerOptions.setContext(context);
        initLocationServices(connectTrackerOptions, intent);
        initPushNotifications(connectTrackerOptions, intent);
        initCallbacks(connectTrackerOptions, intent, callback);
        ConnectTracker.init(connectTrackerOptions);
    }

    private static void initCallbacks(ConnectTrackerOptions connectTrackerOptions, Intent intent, ConnectTrackerCallback callback) {
        if (shouldUseFeature(USE_CALLBACKS, intent) && callback != null) {
            connectTrackerOptions.setConnectTrackerCallback(callback);
        }
    }

    private static void initPushNotifications(ConnectTrackerOptions connectTrackerOptions, Intent intent) {
        if (shouldUseFeature(USE_PUSH_NOTIFICATION_SERVICES, intent)) {
            connectTrackerOptions.setConnectPushNotification(getConnectTrackerNotification(connectTrackerOptions.getContext()));
        }
    }

    private static void initLocationServices(ConnectTrackerOptions connectTrackerOptions, Intent intent) {
        if (shouldUseFeature(USE_LOCATION_SERVICES, intent)) {
            connectTrackerOptions.setUseLocationServices(true);
        }
    }

    private static ConnectPushNotification getConnectTrackerNotification(Context context) {
        ConnectPushNotification push = new ConnectPushNotification(context);
        push.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        push.setSmallIcon(android.R.drawable.ic_menu_send);

        push.setChannelName("App Push");
        push.setChannelDescription("App Push Description");

        return push;
    }

    public static boolean shouldUseFeature(String featureKey, Intent intent) {
        boolean hasBundleFeature = false;
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            hasBundleFeature = bundle.containsKey(featureKey);

            if (hasBundleFeature) {
                hasBundleFeature = bundle.getBoolean(featureKey);
            }
        }

        boolean hasFeatureFlavour = false;

        try {
            hasFeatureFlavour = BuildConfig.class.getDeclaredField(featureKey).getBoolean(null);
        } catch (Exception e) {
            LogUtils.d("Error getting feature flavour: " + e.getMessage(), true);
        }

        return hasBundleFeature || hasFeatureFlavour;
    }
}
