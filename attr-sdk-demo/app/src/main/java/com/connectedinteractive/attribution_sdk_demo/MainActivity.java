package com.connectedinteractive.attribution_sdk_demo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.connectedinteractive.attribution_sdk_demo.utils.Constants;
import com.connectedinteractive.attribution_sdk_demo.utils.LogUtils;
import com.connectedinteractive.attribution_sdk_demo.utils.MockLocationUtils;
import com.connectedinteractive.attribution_sdk_demo.utils.SharedPreferenceUtils;
import com.connectedinteractive.connectsdk.ConnectTracker;

public class MainActivity extends com.connectedinteractive.attribution_sdk_demo.CallbackActivity {
    public static final String MOCK_LOCATION = "mock_location";
    public static final String MOCK_LOCATION_TIMEOUT = "mock_location_timeout";
    public static final String ASK_EXTERNAL_STORAGE_PERMISSION = "ask_external_storage_permission";

    private static final String TAG = "MainActivity";
    private static final int LOCATION_PERMISSION_REQUEST = 0;

    public static final String CUSTOM_EVENT_WP = "test custom event wp";
    public static final String CUSTOM_EVENT_SP = "test custom event sp %(*)(*()";
    public static final String CUSTOM_EVENT_NUMBER = "test custom event number hahahahahahahahahhahahahhahahahahhaha";
    public static final String CUSTOM_EVENT_DEMO = "demo_app_custom_event";
    public static final String CUSTOM_EVENT_DEMO_BIGGER_100_CHARS = "demo_app_custom_event_bigger_100_chars";
    public static final String CUSTOM_EVENT_ZERO_VALUE = "demo_app_custom_event_zero_value";
    public static final String CUSTOM_EVENT_ONE_VALUE = "demo_app_custom_event_one_value";
    public static final String CUSTOM_EVENT_EMPTY_STRING_VALUE = "demo_app_custom_event_empty_value";

    public static final String CUSTOM_EVENT_WP_VALUE = "1234";
    public static final String CUSTOM_EVENT_SP_VALUE = "ydydyd7123&235%812736818**(*&";
    public static final String CUSTOM_EVENT_NUMBER_VALUE = "test_value";
    public static final String CUSTOM_EVENT_DEMO_BIGGER_100_CHARS_VALUE = "demo_app_custom_event_value_demo_app_custom_event_value_demo_app_custom_event_value_demo_app_custom_event_value_demo_app_custom_event_value_demo_app_custom_event_value_demo_app_custom_event_value";
    public static final String CUSTOM_EVENT_ZERO_VALUE_VALUE = "0";
    public static final String CUSTOM_EVENT_ONE_VALUE_VALUE = "1";
    public static final String CUSTOM_EVENT_EMPTY_STRING_VALUE_VALUE = "";

    public static final int MULTIPLE_EVENTS_COUNT = 5;

    public static double latitude;
    public static double longitude;

    private WebView webView;
    private TextView textViewEvents;

    private Button toggleSDKButton;


    private MockLocationUtils locationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleSDKButton = (Button) findViewById(R.id.toggle_sdk);

        LogUtils.d("Main activity on create called.");

        if (com.connectedinteractive.attribution_sdk_demo.ConnectTrackerInitHelper.shouldUseFeature(MOCK_LOCATION, getIntent())) {
            locationUtils = new MockLocationUtils(this, !com.connectedinteractive.attribution_sdk_demo.ConnectTrackerInitHelper.shouldUseFeature(MOCK_LOCATION_TIMEOUT, getIntent()));
            if (latitude != 0 && longitude != 0) {
                locationUtils.setLocation(latitude, longitude);
                latitude = 0;
                longitude = 0;
            }
        }

        //THIS IS REQUIRED BY THE TESTS TO CHECK THE NEXT INSTALL ID/DONT REMOVE
        askExternalStoragePermission();

        LogUtils.d("Lifecycle - onCreate finished");
    }

    public void onClickSaveCustomPreference(View v) {
        SharedPreferenceUtils.getPreferences(this, Constants.BACKUP_RULES_PREFERENCE_NAME)
                .edit()
                .putString(Constants.BACKUP_RULES_PREFERENCE_KEY, Constants.BACKUP_RULES_PREFERENCE_VALUE)
                .commit();
    }

    public void onclickPrintCustomPreference(View v) {
        String value = SharedPreferenceUtils.getTestBackupRulesItem(this);
        Log.d("[ConnectSdk]", "Value shared preferences: " + value);
    }

    private void initConnectTrackerWithOptions() {
        initLocationServices();

        com.connectedinteractive.attribution_sdk_demo.ConnectTrackerInitHelper.initConnectTracker(this, getIntent(), getCallback());
    }

    private void initLocationServices() {
        if (com.connectedinteractive.attribution_sdk_demo.ConnectTrackerInitHelper.shouldUseFeature(com.connectedinteractive.attribution_sdk_demo.ConnectTrackerInitHelper.USE_LOCATION_SERVICES, getIntent())) {
            this.askLocationPermission();
        }
    }

    private void askExternalStoragePermission() {
        if (com.connectedinteractive.attribution_sdk_demo.ConnectTrackerInitHelper.shouldUseFeature(ASK_EXTERNAL_STORAGE_PERMISSION, getIntent())) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 123);
            }
        }
    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ConnectTracker.onWillRequestLocationPermission();
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, LOCATION_PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ConnectTrackerInitHelper.shouldUseFeature(ConnectTrackerInitHelper.USE_LOCATION_SERVICES, getIntent())) {
            if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ConnectTracker.onLocationPermissionGranted();

                    if (ConnectTrackerInitHelper.shouldUseFeature(MOCK_LOCATION, getIntent()) && !ConnectTrackerInitHelper.shouldUseFeature(MOCK_LOCATION_TIMEOUT, getIntent())) {
                        locationUtils.setLocation(MockLocationUtils.VANCOUVER_LATITUDE, MockLocationUtils.VANCOUVER_LONGITUDE);
                    }
                } else {
                    ConnectTracker.onLocationPermissionDenied();
                }
            }
        }
    }

    @Override
    protected void onResume()  {
        Log.i(TAG, "Life Cycle MainActivity.onResume()");
        super.onResume();
    }

    @Override
    protected void onPause()  {
        Log.i(TAG, "Life Cycle MainActivity.onPause()");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Life Cycle MainActivity.onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (com.connectedinteractive.attribution_sdk_demo.ConnectTrackerInitHelper.shouldUseFeature(com.connectedinteractive.attribution_sdk_demo.ConnectTrackerInitHelper.USE_LOCATION_SERVICES, getIntent())) {
            ConnectTracker.onStop();
        }
        Log.i(TAG, "Life Cycle MainActivity.onStop()");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "Life Cycle MainActivity.onRestart()");
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest. xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, com.connectedinteractive.attribution_sdk_demo.OtherActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_multiple_events) {
            for (int i = 0; i < MULTIPLE_EVENTS_COUNT; i++) {
                ConnectTracker.trackEvent("track_event", "value_" + String.valueOf(i));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickCustom(View v) {
        if (!ConnectTracker.isInitialized()) {
            LogUtils.e("Connect Tracker SDK not initialized");
            return;
        }

        ConnectTracker.trackEvent(CUSTOM_EVENT_WP, CUSTOM_EVENT_WP_VALUE);
        ConnectTracker.trackEvent(CUSTOM_EVENT_SP, CUSTOM_EVENT_SP_VALUE);
        ConnectTracker.trackEvent(CUSTOM_EVENT_NUMBER, CUSTOM_EVENT_NUMBER_VALUE);
        ConnectTracker.trackEvent(CUSTOM_EVENT_DEMO);
        ConnectTracker.trackEvent(CUSTOM_EVENT_DEMO_BIGGER_100_CHARS, CUSTOM_EVENT_DEMO_BIGGER_100_CHARS_VALUE);

        ConnectTracker.trackEvent(CUSTOM_EVENT_ZERO_VALUE, CUSTOM_EVENT_ZERO_VALUE_VALUE);
        ConnectTracker.trackEvent(CUSTOM_EVENT_ONE_VALUE, CUSTOM_EVENT_ONE_VALUE_VALUE);
        ConnectTracker.trackEvent(CUSTOM_EVENT_EMPTY_STRING_VALUE, CUSTOM_EVENT_EMPTY_STRING_VALUE_VALUE);
    }

    public void onClickToggleSDK(View v){
        if (ConnectTracker.isTrackingOn()) {
            ConnectTracker.turnOffTracking();
            toggleSDKButton.setText(R.string.enable_sdk_button_str);
        } else {
            ConnectTracker.turnOnTracking();
            toggleSDKButton.setText(R.string.disable_sdk_button_str);
        }
    }

    public void onClickInitializeSdk(View view) {
        this.initConnectTrackerWithOptions();

        findViewById(R.id.initialize_sdk).setEnabled(false);

        Toast.makeText(this, "SDK has been initialized", Toast.LENGTH_SHORT).show();;
    }
}
