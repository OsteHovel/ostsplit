package com.ostsoft.smsplit.split;

import com.ostsoft.smsplit.xml.config.ConfigXML;
import com.ostsoft.smsplit.xml.config.ItemBox;
import com.ostsoft.smsplit.xml.config.action.Action;
import com.ostsoft.smsplit.xml.config.action.ActionXML;
import com.ostsoft.smsplit.xml.config.action.Matching;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LiveSplit implements Split {
    private static Logger logger = Logger.getLogger(LiveSplit.class.getName());

    private Socket socket;
    private OutputStreamWriter osw;
    private BufferedReader in;

    public LiveSplit(ConfigXML configXML) {
        this(configXML.splitters.liveSplit.host, configXML.splitters.liveSplit.port, configXML.splitters.liveSplit.timeout);
    }

    public LiveSplit(String host, int port, int timeout) {
        try {
            socket = new Socket(host, port);
            socket.setKeepAlive(true);
            socket.setSoTimeout(timeout);
            osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getSplitIndex() {
        if (!isConnected()) {
            return -1;
        }
        String splitIndex = sendAndReturnResult("getsplitindex");
        if (splitIndex == null) {
            return -1;
        }
        return Integer.parseInt(splitIndex);
    }

    public String getFinalTime() {
        return sendAndReturnResult("getfinaltime");
    }

    public String getPBTime() {
        return sendAndReturnResult("getfinaltime Personal Best");
    }


    public String getCurrentSplitName() {
        return sendAndReturnResult("getcurrentsplitname");
    }

    public String getPreviousSplitName() {
        return sendAndReturnResult("getprevioussplitname");
    }

    public void split() {
        if (!isConnected()) {
            return;
        }
        try {
            send(osw, "split");
        }
        catch (IOException e) {
            closeSocket();
            e.printStackTrace();
        }
    }


    public void startTimer() {
        if (!isConnected()) {
            return;
        }
        try {
            send(osw, "starttimer");
        }
        catch (IOException e) {
            closeSocket();
            e.printStackTrace();
        }
    }


    public void startOrSplit() {
        if (!isConnected()) {
            return;
        }
        try {
            send(osw, "startorsplit");
        }
        catch (IOException e) {
            closeSocket();
            e.printStackTrace();
        }
    }

    public void unsplit() {
        if (!isConnected()) {
            return;
        }
        try {
            send(osw, "unsplit");
        }
        catch (IOException e) {
            closeSocket();
            e.printStackTrace();
        }
    }

    public void skipSplit() {
        if (!isConnected()) {
            return;
        }
        try {
            send(osw, "skipsplit");
        }
        catch (IOException e) {
            closeSocket();
            e.printStackTrace();
        }
    }

    public void reset() {
        if (!isConnected()) {
            return;
        }
        try {
            send(osw, "reset");
        }
        catch (IOException e) {
            closeSocket();
            e.printStackTrace();
        }
    }

    public String getDelta() {
        return sendAndReturnResult("getdelta");
    }

    public String getLastSplitTime() {
        return sendAndReturnResult("getlastsplittime");
    }

    public String getCurrentTime() {
        return sendAndReturnResult("getcurrenttime");
    }

    private String sendAndReturnResult(String command) {
        if (!isConnected()) {
            return null;
        }
        try {
            send(osw, command);
            return in.readLine();
        }
        catch (SocketTimeoutException ignored) {
            return null;
        }
        catch (IOException e) {
            closeSocket();
            e.printStackTrace();
        }
        return null;
    }

    private void send(OutputStreamWriter outputStreamWriter, String command) throws IOException {
        if (!isConnected()) {
            return;
        }
        outputStreamWriter.write(command + "\r\n");
        outputStreamWriter.flush();
    }

    private void closeSocket() {
        try {
            osw.close();
            in.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return socket != null
                && socket.isConnected()
                && !socket.isClosed();
    }

    @Override
    public void itemSplit(Set<ItemBox> itemsToSplitOn) {
        for (ItemBox itemBox : itemsToSplitOn) {
            for (ActionXML action : itemBox.actions) {
                if (action.action == Action.STARTTIMER) {
                    logger.log(Level.INFO, "Starting Livesplit timer");
                    startTimer();
                    break;
                }
                else if (action.action == Action.RESET) {
                    logger.log(Level.INFO, "Resetting Livesplit timer");
                    reset();
                    break;
                }
            }
        }

        String currentSplitName = getCurrentSplitName();
        if (currentSplitName != null) {
            logger.log(Level.INFO, "Current LiveSplit SplitName: " + currentSplitName + " checking against actions...");
            for (ItemBox itemBox : itemsToSplitOn) {
                for (ActionXML action : itemBox.actions) {
                    if (action.action != Action.SPLIT) {
                        continue;
                    }

                    if (action.matching == Matching.CONTAINS && currentSplitName.contains(action.name)) {
                        logger.log(Level.INFO, "LiveSplit is splitting on " + action.name + " with matching " + action.matching.name());
                        split();
                    }
                    else if (action.matching == Matching.CONTAINS_INSENSITIVE && currentSplitName.toLowerCase().contains(action.name.toLowerCase())) {
                        logger.log(Level.INFO, "LiveSplit is splitting on " + action.name + " with matching " + action.matching.name());
                        split();
                    }
                    else if (action.matching == Matching.EQUALS && currentSplitName.equals(action.name)) {
                        logger.log(Level.INFO, "LiveSplit is splitting on " + action.name + " with matching " + action.matching.name());
                        split();
                    }
                    else if (action.matching == Matching.EQUALS_INSENSITIVE && currentSplitName.equalsIgnoreCase(action.name)) {
                        logger.log(Level.INFO, "LiveSplit is splitting on " + action.name + " with matching " + action.matching.name());
                        split();
                    }
                }
            }
        }
        else {
            logger.log(Level.WARNING, "Current split received from LiveSplit was null, are your timer running?");
        }
    }
}
