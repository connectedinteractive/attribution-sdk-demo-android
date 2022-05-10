package com.connectedinteractive.attribution_sdk_demo.model;

import com.connectedinteractive.connectsdk.ConnectTrackerAttribution;
import com.connectedinteractive.connectsdk.ConnectTrackerEvent;
import com.connectedinteractive.connectsdk.ConnectTrackerFailedEvent;
import com.connectedinteractive.connectsdk.ConnectTrackerSession;
import com.connectedinteractive.connectsdk.ConnectTrackerSessionFailed;

import java.util.ArrayList;
import java.util.List;

public class CallbackEventList {
    private List<ConnectTrackerEvent> eventsTracked;
    private List<ConnectTrackerFailedEvent> eventsFailure;

    private List<ConnectTrackerSession> sessionStartSuccess;
    private List<ConnectTrackerSessionFailed> sessionStartFailed;

    private List<ConnectTrackerAttribution> attribution;

    public CallbackEventList() {
        setEventsTracked(new ArrayList<>());
        setEventsFailure(new ArrayList<>());
        setSessionStartSuccess(new ArrayList<>());
        setSessionStartFailed(new ArrayList<>());
        setAttribution(new ArrayList<>());
    }

    public List<ConnectTrackerEvent> getEventsTracked() {
        return eventsTracked;
    }

    public void setEventsTracked(List<ConnectTrackerEvent> eventsTracked) {
        this.eventsTracked = eventsTracked;
    }

    public List<ConnectTrackerFailedEvent> getEventsFailure() {
        return eventsFailure;
    }

    public void setEventsFailure(List<ConnectTrackerFailedEvent> eventsFailure) {
        this.eventsFailure = eventsFailure;
    }

    public List<ConnectTrackerSession> getSessionStartSuccess() {
        return sessionStartSuccess;
    }

    public void setSessionStartSuccess(List<ConnectTrackerSession> sessionStartSuccess) {
        this.sessionStartSuccess = sessionStartSuccess;
    }

    public List<ConnectTrackerSessionFailed> getSessionStartFailed() {
        return sessionStartFailed;
    }

    public void setSessionStartFailed(List<ConnectTrackerSessionFailed> sessionStartFailed) {
        this.sessionStartFailed = sessionStartFailed;
    }

    public List<ConnectTrackerAttribution> getAttribution() {
        return attribution;
    }

    public void setAttribution(List<ConnectTrackerAttribution> attribution) {
        this.attribution = attribution;
    }
}
