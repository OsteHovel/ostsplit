package com.ostsoft.games.ostsplit.display;

import com.ostsoft.games.ostsplit.AutoData;
import com.ostsoft.games.ostsplit.EditorStatusBar;
import com.ostsoft.games.ostsplit.OstSplit;
import com.ostsoft.games.ostsplit.display.config.CapturePanel;
import com.ostsoft.games.ostsplit.display.itembox.ItemBoxPanel;
import com.ostsoft.games.ostsplit.display.panel.AutoTabbedPane;
import com.ostsoft.games.ostsplit.display.panel.MultiBorderLayout;
import com.ostsoft.games.ostsplit.observer.EventType;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;

public class AutoWindow {
    private final AutoActions autoActions = new AutoActions(this);
    private final AutoTabbedPane tabbedPane = new AutoTabbedPane();
    private final AutoData autoData;
    private final JFrame frame = new JFrame();
    ;

    public AutoWindow(AutoData autoData) {
        this.autoData = autoData;
        setTitle();
        frame.setSize(800, 600);
        frame.setJMenuBar(new AutoMenu(this));

        frame.getContentPane().setLayout(new MultiBorderLayout());
        frame.getContentPane().add(tabbedPane, MultiBorderLayout.CENTER);
        tabbedPane.addTab("Capture", new CapturePanel(autoData));
        tabbedPane.addTab("Item Boxes", new ItemBoxPanel(autoData));
        tabbedPane.addTab("Log", new LogPanel(autoData));
        frame.add(new EditorStatusBar(autoData), MultiBorderLayout.SOUTH);

        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (autoData.isConfigChanged()) {
                    int returnDialogValue = JOptionPane.showConfirmDialog(frame,
                            "You have unsaved changes, do you want save them?", OstSplit.TITLE,
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (returnDialogValue == JOptionPane.YES_OPTION) {
                        autoData.fireEvent(EventType.SAVE);
                        autoData.save(false);
                    }
                    else if (returnDialogValue == JOptionPane.CANCEL_OPTION) {
                        // Cancel
                        return;
                    }
                }
                // Exit
                System.exit(0);
            }
        });

        autoData.addObserver((eventType, message) -> {
            if (eventType == EventType.CONFIG_UPDATE) {
                setTitle();
            }
        });

        frame.setVisible(true);
    }

    private void setTitle() {
        frame.setTitle(OstSplit.TITLE + (autoData.isConfigChanged() ? " (Unsaved changes)" : ""));
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

    public JFrame getFrame() {
        return frame;
    }

    public AutoActions getAutoActions() {
        return autoActions;
    }

    public AutoData getAutoData() {
        return autoData;
    }

    public void exit() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
