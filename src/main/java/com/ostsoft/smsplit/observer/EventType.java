package com.ostsoft.smsplit.observer;

public enum EventType {
    // System
    UNDO_REDO_CHANGED,
    STATUS_BAR_MESSAGE,
    CLEAR_UNDO_REDO, // Clears the Undo/Redo queue

    // General
    LOAD, // Load new rom
    SAVE, // Save rom
    SAVE_AS, // Save rom as new fileName
    CLOSE, // Close current rom
    EXIT, // Close editor

    // Animation
    UPDATED_GAME_IMAGE, ITEMBOX_RECTANGLE_SELECTED, ITEMBOX_RECTANGLE_MOVED, ITEMBOX_SELECTED, CONFIG_UPDATE,


}
