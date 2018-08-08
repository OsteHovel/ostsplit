package com.ostsoft.games.ostsplit;

import com.ostsoft.games.ostsplit.observer.EventType;
import com.ostsoft.games.ostsplit.observer.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
