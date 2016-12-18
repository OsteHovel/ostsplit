package com.ostsoft.smsplit.capture;

import com.ostsoft.smsplit.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CaptureUtil {
    public static Rectangle autoAdjust(BufferedImage gameImage, int startX, int startY, int startWidth, int startHeight) {
        int tolerance = 30;

        int offsetX = 0;
        for (int x = 0; x < startWidth; x++) {
            if (!ImageUtil.checkRect(gameImage, x, 0, 1, startHeight, 0, 0, 0, tolerance)) {
                offsetX = x - 1;
                break;
            }
        }

        int offsetY = 0;
        for (int y = 0; y < startWidth; y++) {
            if (!ImageUtil.checkRect(gameImage, 0, y, startWidth, 1, 0, 0, 0, tolerance)) {
                offsetY = y - 1;
                break;
            }
        }

        int offsetW = 0;
        for (int w = startWidth - 1; w >= 0; w--) {
            if (!ImageUtil.checkRect(gameImage, w, 0, 1, startHeight, 0, 0, 0, tolerance)) {
                offsetW = w - 1;
                break;
            }
        }

        int offsetH = 0;
        for (int h = startHeight - 1; h >= 0; h--) {
            if (!ImageUtil.checkRect(gameImage, 0, h, startWidth, 1, 0, 0, 0, tolerance)) {
                offsetH = h - 1;
                break;
            }
        }

        return new Rectangle(startX + offsetX, startY + offsetY, (offsetW - offsetX), (offsetH - offsetY));
    }
}
