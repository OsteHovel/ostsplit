package com.ostsoft.smsplit.observer;

public interface Observer {

    void handleEvent(EventType eventType, String message);

}
