package com.ostsoft.games.ostsplit.capture;

import com.ostsoft.games.ostsplit.util.ImageUtil;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class CaptureUtil {
    public static Rectangle autoAdjust(BufferedImage gameImage, int startX, int startY, int startWidth, int startHeight) {
        int tolerance = 30;

        int offsetX = 0;
        for (int x = 0; x < startWidth; x++) {
            if (!ImageUtil.checkRect(gameImage, x, 0, 1, startHeight, 0, 0, 0, tolerance)) {
                offsetX = x;
                break;
            }
        }

        int offsetY = 0;
        for (int y = 0; y < startWidth; y++) {
            if (!ImageUtil.checkRect(gameImage, 0, y, startWidth, 1, 0, 0, 0, tolerance)) {
                offsetY = y;
                break;
            }
        }

        int offsetW = 0;
        for (int w = startWidth; w >= 1; w--) {
            if (!ImageUtil.checkRect(gameImage, w - 1, 0, 1, startHeight, 0, 0, 0, tolerance)) {
                offsetW = w;
                break;
            }
        }

        int offsetH = 0;
        for (int h = startHeight; h >= 1; h--) {
            if (!ImageUtil.checkRect(gameImage, 0, h - 1, startWidth, 1, 0, 0, 0, tolerance)) {
                offsetH = h;
                break;
            }
        }

        int newX = startX + offsetX;
        int newY = startY + offsetY;
        int newWidth = offsetW - offsetX;
        int newHeight = offsetH - offsetY;
        return new Rectangle(newX, newY, newWidth, newHeight);
    }
}
