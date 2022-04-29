package com.connectedinteractive.attribution_sdk_demo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {
    static final String LAST_LOCATION_TIMESTAMP = "last_location_timestamp";

    public static SharedPreferences getPreferences(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, 0);
        return sharedPreferences;
    }

    public static SharedPreferences getPreferences(Context context) {
        return getPreferences(context, getPreferenceName(context));
    }

    public static String getAdvertisingId(Context context) {
        String advertisingId = getPreferences(context).getString("advertising_id", null);
        return advertisingId;
    }

    public static void setAdvertisingId(Context context, String advertisingId) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("advertising_id", advertisingId);
        editor.apply();
    }

    public static String getTrackingId(Context context) {
        return getPreferences(context).getString("tracking_id", null);
    }

    public static long getImpressionId(Context context) {
        return getPreferences(context).getLong("impression_id", 0);
    }

    public static String getTestBackupRulesItem(Context context) {
        return getPreferences(context, Constants.BACKUP_RULES_PREFERENCE_NAME).getString(Constants.BACKUP_RULES_PREFERENCE_KEY, null);
    }

    private static String getPreferenceName(Context context) {
        return "ConnectedInteractivePrefsFile_" + context.getPackageName();
    }

    //region location fields
    public static void setLastLocationTimestamp(Context context, long timestamp) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putLong(LAST_LOCATION_TIMESTAMP, timestamp);
        editor.apply();
    }
}
