package com.ostsoft.games.ostsplit.util;

import java.awt.image.BufferedImage;

public class ImageUtil {
    public static boolean checkRect(BufferedImage image, int x, int y, int width, int height, int r, int g, int b, int tolerance) {
        // Pre-calc RGB "tolerance" values out of the loop (min is 0 and max is 255)
        int minR = Math.max(r - tolerance, 0);
        int minG = Math.max(g - tolerance, 0);
        int minB = Math.max(b - tolerance, 0);
        int maxR = Math.min(r + tolerance, 255);
        int maxG = Math.min(g + tolerance, 255);
        int maxB = Math.min(b + tolerance, 255);

        int xMax = Math.min(x + width, image.getWidth());
        int yMax = Math.min(y + height, image.getHeight());
        for (int i = x; i < xMax; i++) {
            for (int j = y; j < yMax; j++) {
                // get single RGB pixel
                int color = image.getRGB(i, j);

                // get individual RGB values of that pixel
                // (could use Java's Color class but this is probably a little faster)
                int red = (color >> 16) & 0x000000FF;
                int green = (color >> 8) & 0x000000FF;
                int blue = (color) & 0x000000FF;

                if ((red >= minR && red <= maxR) &&
                        (green >= minG && green <= maxG) &&
                        (blue >= minB && blue <= maxB)) {
                } else {
                    return false;
                }
            }
        }
        return true;
    }

}
