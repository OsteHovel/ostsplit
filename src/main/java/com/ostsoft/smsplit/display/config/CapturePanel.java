package com.ostsoft.smsplit.display.config;

import com.ostsoft.smsplit.AutoData;
import com.ostsoft.smsplit.capture.Capture;
import com.ostsoft.smsplit.capture.CaptureMethod;
import com.ostsoft.smsplit.capture.nullcapture.NullCapture;
import com.ostsoft.smsplit.capture.screencapture.ScreenCapture;
import com.ostsoft.smsplit.capture.windowcapture.WindowCapture;
import com.ostsoft.smsplit.display.GamePanel;
import com.ostsoft.smsplit.display.panel.AutoPanel;
import com.ostsoft.smsplit.display.panel.PanelData;
import com.ostsoft.smsplit.display.panel.PanelEvent;
import com.ostsoft.smsplit.observer.EventType;
import com.ostsoft.smsplit.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CapturePanel extends AutoPanel {
    private final Observer observer;
    private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private final GamePanel gamePanel = new GamePanel();

    public CapturePanel(AutoData autoData) {
        super(autoData, new PanelData(autoData));
        setLayout(new BorderLayout(0, 0));

        JComboBox<CaptureMethod> comboBox = new JComboBox<>(CaptureMethod.values());
        JButton autoadjust = new JButton("Autoadjust");
        autoadjust.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Capture capture = autoData.getCapture();
                if (capture != null) {
                    capture.autoAdjust();
                }
            }
        });
        add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, comboBox, autoadjust), BorderLayout.NORTH);

        comboBox.addActionListener(e -> {
            CaptureMethod selectedItem = (CaptureMethod) comboBox.getModel().getSelectedItem();
            autoData.config.capture.method = selectedItem;
            switch (selectedItem) {
                case SCREENCAPTURE:
                    autoData.setCapture(new ScreenCapture(autoData));
                    autoData.config.capture.method = CaptureMethod.SCREENCAPTURE;
                    break;
                case WINDOWCAPTURE:
                    autoData.setCapture(new WindowCapture(autoData));
                    autoData.config.capture.method = CaptureMethod.WINDOWCAPTURE;
                    break;
                default:
                    autoData.setCapture(new NullCapture());
                    autoData.config.capture.method = CaptureMethod.NULLCAPTURE;
                    break;
            }

            setSettingsPanel(autoData.getCapture().getSettingsPanel());
            autoData.fireEvent(EventType.CONFIG_UPDATE);
        });
        comboBox.getModel().setSelectedItem(autoData.config.capture.method);

        observer = (eventType, message) ->
        {
            switch (eventType) {
                case UPDATED_GAME_IMAGE:
                    if (autoData.getGameImage() != null) {
                        gamePanel.setImage(autoData.getGameImage());
                    }
                    break;
                case CONFIG_UPDATE:

                    break;
            }
        };

        autoData.addObserver(observer);

        splitPane.add(new JScrollPane(gamePanel), JSplitPane.RIGHT);
        splitPane.setResizeWeight(0.5d);
        splitPane.setDividerLocation(0.5d);
        add(splitPane, BorderLayout.CENTER);
    }

    @Override
    public void handlePanelEvent(PanelEvent panelEvent) {
        super.handlePanelEvent(panelEvent);
        if (panelEvent == PanelEvent.CLOSEPANEL) {
            autoData.removeObserver(observer);
        }
    }

    private void setSettingsPanel(Component component) {
        splitPane.add(component, JSplitPane.LEFT);
    }
}

