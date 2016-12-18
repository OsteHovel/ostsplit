package com.ostsoft.smsplit;

import com.ostsoft.smsplit.observer.EventType;
import com.ostsoft.smsplit.observer.Observer;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class EditorStatusBar extends JPanel implements Observer {
    private final AutoData autoData;
    private final JLabel statusLabel;

    public EditorStatusBar(AutoData autoData) {
        super();
        this.autoData = autoData;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(statusLabel);

        autoData.addObserver(this);
    }

    @Override
    public void handleEvent(EventType eventType, String message) {
        if (message != null) {
            statusLabel.setText(message);
        }
    }
}
