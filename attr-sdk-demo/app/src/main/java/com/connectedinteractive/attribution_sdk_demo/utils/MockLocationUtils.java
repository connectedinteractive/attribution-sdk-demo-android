package com.connectedinteractive.attribution_sdk_demo.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MockLocationUtils {
    private static final String TAG = "MockLocationUtils";
    public static final double VANCOUVER_LATITUDE = 49.249871;
    public static final double VANCOUVER_LONGITUDE = -123.107083;
    public static final double BURNABY_LATITUDE = 49.254533;
    public static final double BURNABY_LONGITUDE = -122.953365;
    public static final float HORIZONTAL_ACCURACY = 3.0f;

    private FusedLocationProviderClient locationProviderClient;
    private Context context;

    public MockLocationUtils(Context context, boolean startWithLocation) {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.context = context;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationProviderClient.setMockMode(true);
        }

        if (startWithLocation) {
            setLocation(VANCOUVER_LATITUDE, VANCOUVER_LONGITUDE);
        }
    }

    public void setLocation(double latitude, double longitude) {
        Location location = new Location(LocationManager.NETWORK_PROVIDER);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAccuracy(HORIZONTAL_ACCURACY);
        location.setAltitude(12);
        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        location.setTime(System.currentTimeMillis());

        if (ActivityCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationProviderClient.setMockLocation(location).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                logD(String.format("Location mocked: %s - %s", latitude, longitude));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                logE("Location failed mocked: ", e);
            }
        });
    }

    private void logD(String message, boolean force) {
        LogUtils.d(String.format("%s - %s", TAG, message), force);
    }

    private void logD(String message) {
        logD(message, false);
    }

    private void logE(String message, Throwable e) {
        LogUtils.e(String.format("%s - %s", TAG, message), e);
    }
}
