package com.ostsoft.games.ostsplit.observer;

public interface Observer {

    void handleEvent(EventType eventType, String message);

}
