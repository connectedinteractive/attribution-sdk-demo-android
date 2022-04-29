package com.connectedinteractive.attribution_sdk_demo.utils;

import android.util.Log;

import com.connectedinteractive.connectsdk.BuildConfig;

public class LogUtils {

    private static String LOG_TAG = "[ConnectSdk]";

    public static void i(String message) {
        i(message, false);
    }

    public static void i(String message, boolean forceLog) {
        try {
            if (LogUtils.shouldLog() || forceLog) {
                Log.i(LOG_TAG, message);
            }
        }
        catch (Exception e) {
            //
        }
    }

    public static void e(String message) {
        e(message, false);
    }

    public static void e(String message, boolean forceLog) {
        try {
            if (LogUtils.shouldLog() || forceLog) {
                Log.e(LOG_TAG, message);
            }
        }
        catch (Exception e) {
            //
        }
    }

    public static void e(String message, Throwable throwable) {
        try {
            if (LogUtils.shouldLog()) {
                Log.e(LOG_TAG, message, throwable);
            }
        }
        catch (Exception e) {
            //
        }
    }

    public static void d(String message, boolean forceLog) {
        try {
            if (LogUtils.shouldLog() || forceLog) {
                Log.d(LOG_TAG, message);
            }
        }
        catch (Exception e) {
            //
        }
    }

    public static void d(String message) {
        d(message, false);
    }

    public static void d(String message, Throwable throwable) {
        try {
            if (LogUtils.shouldLog()) {
                Log.d(LOG_TAG, message, throwable);
            }
        }
        catch (Exception e) {
            //
        }
    }

    public static void SandboxLog(String message, boolean forceLog) {
        try {
            if (LogUtils.shouldLog() || forceLog) {
                LogUtils.d(String.format("CONNECTSDK_SANDBOX - %s", message));
            }
        }
        catch (Exception e) {

        }
    }

    public static void SandboxLog(String message) {
        LogUtils.SandboxLog(message, false);
    }

    private static boolean shouldLog() {
        if (BuildConfig.DEBUG) {
            return true;
        } else {
            return false;
        }
    }
}
