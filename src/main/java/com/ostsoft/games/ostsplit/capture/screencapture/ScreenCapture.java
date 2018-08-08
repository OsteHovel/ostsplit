package com.ostsoft.games.ostsplit.capture.screencapture;

import com.ostsoft.games.ostsplit.AutoData;
import com.ostsoft.games.ostsplit.capture.Capture;
import com.ostsoft.games.ostsplit.capture.CaptureUtil;

import javax.swing.JPanel;
import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.geom.Rectangle2D.union;

public class ScreenCapture implements Capture {
    private static Logger logger = Logger.getLogger(ScreenCapture.class.getName());
    private final AutoData autoData;
    private final Rectangle entireDesktop;
    private final ScreenCapturePanel screenCapturePanel;
    private Robot robot;

    public ScreenCapture(AutoData autoData) {
        this.autoData = autoData;
        screenCapturePanel = new ScreenCapturePanel(autoData);

        try {
            robot = new Robot();
        } catch (AWTException e) {
            logger.log(Level.SEVERE, "Error creating new robot: " + e.getMessage());
            e.printStackTrace();
        }

        Rectangle2D.Double screen = new Rectangle2D.Double();
        GraphicsEnvironment localGE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (GraphicsDevice gd : localGE.getScreenDevices()) {
            for (GraphicsConfiguration graphicsConfiguration : gd.getConfigurations()) {
                union(screen, graphicsConfiguration.getBounds(), screen);
            }
        }
        this.entireDesktop = screen.getBounds();
    }

    @Override
    public BufferedImage capture() {
        if (robot == null) {
            return null;
        }
        return robot.createScreenCapture(new Rectangle(entireDesktop.x + autoData.config.capture.screenCapture.x, entireDesktop.y + autoData.config.capture.screenCapture.y, autoData.config.capture.screenCapture.width, autoData.config.capture.screenCapture.height));
    }

    @Override
    public JPanel getSettingsPanel() {
        screenCapturePanel.loadSettings();
        return screenCapturePanel;
    }

    @Override
    public void autoAdjust() {
        Rectangle rectangle = CaptureUtil.autoAdjust(capture(), autoData.config.capture.screenCapture.x, autoData.config.capture.screenCapture.y, autoData.config.capture.screenCapture.width, autoData.config.capture.screenCapture.height);
        autoData.config.capture.screenCapture.x = rectangle.x;
        autoData.config.capture.screenCapture.y = rectangle.y;
        autoData.config.capture.screenCapture.width = rectangle.width;
        autoData.config.capture.screenCapture.height = rectangle.height;
        screenCapturePanel.loadSettings();
        autoData.setConfigChanged(true);
    }


}
