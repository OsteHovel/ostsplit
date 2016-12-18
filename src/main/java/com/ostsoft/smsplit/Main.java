package com.ostsoft.smsplit;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final float cycleTime = 250f;
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        AutoSplit autoSplit = new AutoSplit();

        float time;
        long timeStart = System.nanoTime();
        long timeEnd;
        while (true) {
            long timeSinceLastUpdate = (System.nanoTime() - timeStart) / 1000000;
            timeStart = System.nanoTime();

            autoSplit.cycle(timeSinceLastUpdate);
            timeEnd = System.nanoTime();
            time = (timeEnd - timeStart) / 1000000.0f;

            try {
                if (time < cycleTime) {
                    Thread.sleep((long) (cycleTime - time));
                } else {
                    logger.log(Level.WARNING, "Warning! Skipping sleep! Cycle-time: " + time);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
