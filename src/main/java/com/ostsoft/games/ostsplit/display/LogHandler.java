package com.ostsoft.games.ostsplit.display;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {
    private final JTextPane textPane;

    public LogHandler(JTextPane textPane) {
        this.textPane = textPane;
    }

    @Override
    public void publish(LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }

        try {
            StyledDocument doc = textPane.getStyledDocument();
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setFontSize(set, 14);
            doc.insertString(0, record.getMillis() + " - " + record.getLevel() + ": " + record.getMessage() + "\n", set);


        } catch (BadLocationException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
