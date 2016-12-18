package com.ostsoft.smsplit.capture.windowcapture;

import com.ostsoft.smsplit.AutoData;
import com.ostsoft.smsplit.capture.Capture;
import com.ostsoft.smsplit.capture.CaptureUtil;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.GDI32Util;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowCapture implements Capture {
    private static Logger logger = Logger.getLogger(WindowCapture.class.getName());
    private final AutoData autoData;
    private final WindowCapturePanel windowCapturePanel;
    private WinDef.HWND hwnd = null;

    public WindowCapture(AutoData autoData) {
        this.autoData = autoData;
        this.windowCapturePanel = new WindowCapturePanel(autoData);
        hwnd = findWindow(autoData.config.capture.windowCapture.windowTitle);
    }

    @Override
    public BufferedImage capture() {
        if (hwnd != null && User32.INSTANCE.IsWindow(hwnd) && WindowUtils.getWindowTitle(hwnd).contains(autoData.config.capture.windowCapture.windowTitle)) {
            BufferedImage screenshot = GDI32Util.getScreenshot(hwnd);
            try {
                return screenshot.getSubimage(autoData.config.capture.windowCapture.x, autoData.config.capture.windowCapture.y, autoData.config.capture.windowCapture.width, autoData.config.capture.windowCapture.height);
            } catch (java.awt.image.RasterFormatException e) {
                logger.log(Level.SEVERE, "Error while cropping image: " + e.getMessage());
                return null;
            }
        } else {
            logger.log(Level.SEVERE, "Window does not exist");
            hwnd = findWindow(autoData.config.capture.windowCapture.windowTitle);
        }
        return null;
    }

    @Override
    public JPanel getSettingsPanel() {
        windowCapturePanel.loadSettings();
        return windowCapturePanel;
    }

    @Override
    public void autoAdjust() {
        Rectangle rectangle = CaptureUtil.autoAdjust(capture(), autoData.config.capture.windowCapture.x, autoData.config.capture.windowCapture.y, autoData.config.capture.windowCapture.width, autoData.config.capture.windowCapture.height);
        autoData.config.capture.windowCapture.x = rectangle.x;
        autoData.config.capture.windowCapture.y = rectangle.y;
        autoData.config.capture.windowCapture.width = rectangle.width;
        autoData.config.capture.windowCapture.height = rectangle.height;
        autoData.setConfigChanged(true);
    }

    private WinDef.HWND findWindow(String partialWindowTitle) {
        List<DesktopWindow> windows = WindowUtils.getAllWindows(true);
        for (DesktopWindow window : windows) {
            if (window.getTitle().contains(partialWindowTitle)) {
                return window.getHWND();
            }
        }
        return null;
    }
}
