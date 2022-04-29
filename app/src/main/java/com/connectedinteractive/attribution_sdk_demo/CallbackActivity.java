package com.connectedinteractive.attribution_sdk_demo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.connectedinteractive.attribution_sdk_demo.model.CallbackEventList;
import com.connectedinteractive.attribution_sdk_demo.utils.LogUtils;
import com.connectedinteractive.connectsdk.ConnectTrackerAttribution;
import com.connectedinteractive.connectsdk.ConnectTrackerCallback;
import com.connectedinteractive.connectsdk.ConnectTrackerEvent;
import com.connectedinteractive.connectsdk.ConnectTrackerFailedEvent;
import com.connectedinteractive.connectsdk.ConnectTrackerSession;
import com.connectedinteractive.connectsdk.ConnectTrackerSessionFailed;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;


import java.util.concurrent.atomic.AtomicInteger;

public class CallbackActivity extends AppCompatActivity {
    protected TextView textViewEvents;
    protected CallbackEventList callbackEventList;

    protected void initCallbacksFeature() {
        if (ConnectTrackerInitHelper.shouldUseFeature(ConnectTrackerInitHelper.USE_CALLBACKS, getIntent())) {
            textViewEvents = findViewById(R.id.textViewEvents);
            callbackEventList = new CallbackEventList();
        }
    }

    private AtomicInteger totalCallbacksReceived = new AtomicInteger();
    private AtomicInteger getTotalCallbacksReceivedChunk = new AtomicInteger();
    private int callbackLimitToUpdateUi = 200;

    protected ConnectTrackerCallback getCallback() {
        initCallbacksFeature();
        ConnectTrackerCallback callback = null;
        final Context context = this;
        if (ConnectTrackerInitHelper.shouldUseFeature(ConnectTrackerInitHelper.USE_CALLBACKS, getIntent()) && !ConnectTrackerInitHelper.shouldUseFeature(ConnectTrackerInitHelper.JUST_CALLBACKS_COUNT, getIntent())) {
            callback = getJsonCallback(context);
        } else if (ConnectTrackerInitHelper.shouldUseFeature(ConnectTrackerInitHelper.USE_CALLBACKS, getIntent()) && ConnectTrackerInitHelper.shouldUseFeature(ConnectTrackerInitHelper.JUST_CALLBACKS_COUNT, getIntent())) {
            callback = getCountCallback(context);
        }

        return callback;
    }

    private ConnectTrackerCallback getJsonCallback(Context context) {
        return new ConnectTrackerCallback() {
            @Override
            public void onEventTracked(ConnectTrackerEvent connectTrackerEvent) {
                FirebaseAnalytics.getInstance(context).logEvent(connectTrackerEvent.getName(), connectTrackerEvent.toParameterBundle());
                LogUtils.d("Got callback onEventTracked: " + connectTrackerEvent.getName());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callbackEventList.getEventsTracked().add(connectTrackerEvent);
                        textViewEvents.setVisibility(View.VISIBLE);
                        textViewEvents.setText(new Gson().toJson(callbackEventList));
                        LogUtils.d("Callback events: " + textViewEvents.getText());
                    }
                });
            }

            @Override
            public void onEventTrackFailed(ConnectTrackerFailedEvent connectTrackerFailedEvent) {
                LogUtils.d("Got onEventTrackFailed: " + connectTrackerFailedEvent.getName());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callbackEventList.getEventsFailure().add(connectTrackerFailedEvent);
                        textViewEvents.setText(new Gson().toJson(callbackEventList));
                    }
                });
            }

            @Override
            public void onAttributionChanged(ConnectTrackerAttribution connectTrackerAttribution) {
                LogUtils.d("Got callback onAttributionChanged: " + connectTrackerAttribution.getAttribution().getCampaign());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callbackEventList.getAttribution().add(connectTrackerAttribution);
                        textViewEvents.setText(new Gson().toJson(callbackEventList));
                        LogUtils.d("Callback events: " + textViewEvents.getText());
                    }
                });
            }

            @Override
            public void onSessionStartSuccess(ConnectTrackerSession connectTrackerSession) {
                LogUtils.d("Got callback onSessionStartSuccess: " + connectTrackerSession.getTimestamp());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callbackEventList.getSessionStartSuccess().add(connectTrackerSession);
                        textViewEvents.setText(new Gson().toJson(callbackEventList));
                        LogUtils.d("Callback events: " + textViewEvents.getText());
                    }
                });
            }

            @Override
            public void onSessionStartFailed(ConnectTrackerSessionFailed connectTrackerFailedEvent) {
                LogUtils.d("Got callback onSessionStartFailed: " + connectTrackerFailedEvent.getTimestamp());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callbackEventList.getSessionStartFailed().add(connectTrackerFailedEvent);
                        textViewEvents.setText(new Gson().toJson(callbackEventList));
                        LogUtils.d("Callback events: " + textViewEvents.getText());
                    }
                });
            }
        };
    }

    private ConnectTrackerCallback getCountCallback(Context context) {
        return new ConnectTrackerCallback() {
            @Override
            public void onEventTracked(ConnectTrackerEvent connectTrackerEvent) {
                Log.d("[ConnectSdk]", "Got callback onEventTracked: " + connectTrackerEvent.getName());

                totalCallbacksReceived.incrementAndGet();
                getTotalCallbacksReceivedChunk.incrementAndGet();

                if (getTotalCallbacksReceivedChunk.get() >= callbackLimitToUpdateUi) {
                    LogUtils.d("Will set count callbacks: " + getTotalCallbacksReceivedChunk.get());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewEvents.setText(totalCallbacksReceived.get());
                        }
                    });

                    getTotalCallbacksReceivedChunk.set(0);
                }
            }

            @Override
            public void onEventTrackFailed(ConnectTrackerFailedEvent connectTrackerEvent) {
            }

            @Override
            public void onAttributionChanged(ConnectTrackerAttribution connectTrackerAttribution) {

            }

            @Override
            public void onSessionStartSuccess(ConnectTrackerSession connectTrackerSession) {

            }

            @Override
            public void onSessionStartFailed(ConnectTrackerSessionFailed connectTrackerFailedEvent) {

            }
        };
    }
}
