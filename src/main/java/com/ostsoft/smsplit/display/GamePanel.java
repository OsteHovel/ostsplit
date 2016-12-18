package com.ostsoft.smsplit.display;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends ImagePanel {
    private final ImagePanel imagePanel = new ImagePanel();

    public GamePanel() {
        add(new JScrollPane(imagePanel));
    }

    public void setImage(Image image) {
        imagePanel.setImage(image);
    }
}
