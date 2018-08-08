package com.ostsoft.games.ostsplit.display;

import javax.swing.JScrollPane;
import java.awt.Image;

public class GamePanel extends ImagePanel {
    private final ImagePanel imagePanel = new ImagePanel();

    public GamePanel() {
        add(new JScrollPane(imagePanel));
    }

    public void setImage(Image image) {
        imagePanel.setImage(image);
    }
}
