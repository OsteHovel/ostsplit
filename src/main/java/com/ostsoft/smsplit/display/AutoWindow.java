package com.ostsoft.smsplit.display;

import com.ostsoft.smsplit.AutoData;
import com.ostsoft.smsplit.EditorStatusBar;
import com.ostsoft.smsplit.display.config.CapturePanel;
import com.ostsoft.smsplit.display.itembox.ItemBoxPanel;
import com.ostsoft.smsplit.display.panel.AutoTabbedPane;
import com.ostsoft.smsplit.display.panel.MultiBorderLayout;
import com.ostsoft.smsplit.observer.EventType;
import com.ostsoft.smsplit.xml.XMLUtil;

import javax.swing.*;
import java.awt.*;

public class AutoWindow {
    private final AutoActions autoActions = new AutoActions(this);
    private final AutoTabbedPane tabbedPane = new AutoTabbedPane();
    private final AutoData autoData;
    private final JFrame jFrame = new JFrame();
    ;

    public AutoWindow(AutoData autoData) {
        this.autoData = autoData;
        setTitle();
        jFrame.setSize(800, 600);
        jFrame.setJMenuBar(new AutoMenu(this));

        jFrame.getContentPane().setLayout(new MultiBorderLayout());
        jFrame.getContentPane().add(tabbedPane, MultiBorderLayout.CENTER);
        tabbedPane.addTab("Capture", new CapturePanel(autoData));
        tabbedPane.addTab("Item Boxes", new ItemBoxPanel(autoData));
        tabbedPane.addTab("Log", new LogPanel(autoData));
        jFrame.add(new EditorStatusBar(autoData), MultiBorderLayout.SOUTH);

//        jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
//        setLocation(jFrame, 5, 0);
//        Point location = jFrame.getLocation();
//        location.translate(500, 200);
//        jFrame.setLocation(location);

        jFrame.setVisible(true);

        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int returnDialogValue = JOptionPane.showConfirmDialog(jFrame,
                        "You have unsaved changes, do you want change them?", "Exiting",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (returnDialogValue == JOptionPane.YES_OPTION) {
                    autoData.fireEvent(EventType.SAVE);
                    XMLUtil.encodeConfig("config.xml", autoData.config);

                    System.exit(0);
                } else if (returnDialogValue == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });

        autoData.addObserver((eventType, message) -> {
            if (eventType == EventType.CONFIG_UPDATE) {
                setTitle();
            }
        });
        autoData.setConfigChanged(false);
    }

    private void setTitle() {
        jFrame.setTitle("SMsplit" + (autoData.isConfigChanged() ? "*" : "") + " " + About.VERSION);
    }

    public void setLocation(JFrame jFrame, int device, int configuration) {
        GraphicsEnvironment localGE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = localGE.getScreenDevices();
        if (screenDevices.length <= device) {
            System.err.println("No screenDevice " + device + " max " + screenDevices.length);
            return;
        }

        GraphicsConfiguration[] configurations = screenDevices[device].getConfigurations();
        if (configurations.length <= configuration) {
            System.err.println("No configuration " + configuration + " max " + configurations.length);
            return;
        }
        Rectangle bounds = configurations[configuration].getBounds();
        jFrame.setLocation(bounds.getLocation());
    }

    public JFrame getjFrame() {
        return jFrame;
    }

    public AutoActions getAutoActions() {
        return autoActions;
    }

    public AutoData getAutoData() {
        return autoData;
    }

    public void exit() {
        System.exit(0);
    }
}
