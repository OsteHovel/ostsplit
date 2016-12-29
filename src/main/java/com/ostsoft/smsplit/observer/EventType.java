package com.ostsoft.smsplit.observer;

public enum EventType {
    // System
    UNDO_REDO_CHANGED,
    STATUS_BAR_MESSAGE,
    CLEAR_UNDO_REDO, // Clears the Undo/Redo queue

    // General
    LOAD, // Load
    SAVE, // Save
    SAVE_AS, // Save as new fileName
    EXIT, // Close editor

    // Rectangles
    ITEMBOX_RECTANGLE_SELECTED,
    ITEMBOX_RECTANGLE_MOVED,
    ITEMBOX_SELECTED,

    // Misc
    UPDATED_GAME_IMAGE,
    CONFIG_UPDATE,


}
