package com.ostsoft.smsplit.capture.windowcapture;

import com.ostsoft.smsplit.AutoData;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WindowCapturePanel extends JPanel {
    private final AutoData autoData;
    private final JLabel lblWindowTitle = new JLabel("Window title");
    private final JComboBox<String> windowTitleComboBox = new JComboBox<>();
    private final SpinnerNumberModel xSpinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
    private final SpinnerNumberModel ySpinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
    private final SpinnerNumberModel widthSpinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
    private final SpinnerNumberModel heightSpinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
    private boolean loading = false;

    public WindowCapturePanel(AutoData autoData) {
        this.autoData = autoData;
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{93, 227, 0};
        gridBagLayout.rowHeights = new int[]{34, 33, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        GridBagConstraints gbc_lblWindowTitle = new GridBagConstraints();
        gbc_lblWindowTitle.anchor = GridBagConstraints.WEST;
        gbc_lblWindowTitle.insets = new Insets(0, 0, 5, 5);
        gbc_lblWindowTitle.gridx = 0;
        gbc_lblWindowTitle.gridy = 0;
        add(lblWindowTitle, gbc_lblWindowTitle);

        GridBagConstraints gbc_windowTitleComboBox = new GridBagConstraints();
        gbc_windowTitleComboBox.insets = new Insets(0, 0, 5, 0);
        gbc_windowTitleComboBox.fill = GridBagConstraints.BOTH;
        gbc_windowTitleComboBox.gridx = 1;
        gbc_windowTitleComboBox.gridy = 0;
        add(windowTitleComboBox, gbc_windowTitleComboBox);

        JLabel lblX = new JLabel("X");
        GridBagConstraints gbc_lblX = new GridBagConstraints();
        gbc_lblX.insets = new Insets(0, 0, 5, 5);
        gbc_lblX.gridx = 0;
        gbc_lblX.gridy = 1;
        add(lblX, gbc_lblX);

        JSpinner xSpinner = new JSpinner(xSpinnerModel);
        GridBagConstraints gbc_spinner = new GridBagConstraints();
        gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinner.insets = new Insets(0, 0, 5, 0);
        gbc_spinner.gridx = 1;
        gbc_spinner.gridy = 1;
        add(xSpinner, gbc_spinner);

        JLabel lblY = new JLabel("Y");
        GridBagConstraints gbc_lblY = new GridBagConstraints();
        gbc_lblY.insets = new Insets(0, 0, 5, 5);
        gbc_lblY.gridx = 0;
        gbc_lblY.gridy = 2;
        add(lblY, gbc_lblY);

        JSpinner ySpinner = new JSpinner(ySpinnerModel);
        GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
        gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinner_1.insets = new Insets(0, 0, 5, 0);
        gbc_spinner_1.gridx = 1;
        gbc_spinner_1.gridy = 2;
        add(ySpinner, gbc_spinner_1);

        JLabel lblWidth = new JLabel("Width");
        GridBagConstraints gbc_lblWidth = new GridBagConstraints();
        gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
        gbc_lblWidth.gridx = 0;
        gbc_lblWidth.gridy = 3;
        add(lblWidth, gbc_lblWidth);

        JSpinner widthSpinner = new JSpinner(widthSpinnerModel);
        GridBagConstraints gbc_spinner_2 = new GridBagConstraints();
        gbc_spinner_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinner_2.insets = new Insets(0, 0, 5, 0);
        gbc_spinner_2.gridx = 1;
        gbc_spinner_2.gridy = 3;
        add(widthSpinner, gbc_spinner_2);

        JLabel lblHeight = new JLabel("Height");
        GridBagConstraints gbc_lblHeight = new GridBagConstraints();
        gbc_lblHeight.insets = new Insets(0, 0, 0, 5);
        gbc_lblHeight.gridx = 0;
        gbc_lblHeight.gridy = 4;
        add(lblHeight, gbc_lblHeight);

        JSpinner heightSpinner = new JSpinner(heightSpinnerModel);
        GridBagConstraints gbc_spinner_3 = new GridBagConstraints();
        gbc_spinner_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinner_3.gridx = 1;
        gbc_spinner_3.gridy = 4;
        add(heightSpinner, gbc_spinner_3);

        windowTitleComboBox.addActionListener(e -> {
            if (!loading) {
                if (!autoData.config.capture.windowCapture.partial) {
                    autoData.config.capture.windowCapture.windowTitle = (String) windowTitleComboBox.getModel().getSelectedItem();
                    autoData.setConfigChanged(true);
                }
            }
        });

        xSpinnerModel.addChangeListener(e -> {
            if (!loading) {
                if (xSpinner.getValue() instanceof Integer) {
                    autoData.config.capture.windowCapture.x = (int) xSpinnerModel.getValue();
                    autoData.setConfigChanged(true);
                }
            }
        });

        ySpinnerModel.addChangeListener(e -> {
            if (!loading) {
                if (ySpinnerModel.getValue() instanceof Integer) {
                    autoData.config.capture.windowCapture.y = (int) ySpinnerModel.getValue();
                    autoData.setConfigChanged(true);
                }
            }
        });

        widthSpinnerModel.addChangeListener(e -> {
            if (!loading) {
                if (widthSpinnerModel.getValue() instanceof Integer) {
                    autoData.config.capture.windowCapture.width = (int) widthSpinnerModel.getValue();
                    autoData.setConfigChanged(true);
                }
            }
        });

        heightSpinnerModel.addChangeListener(e -> {
            if (!loading) {
                if (heightSpinnerModel.getValue() instanceof Integer) {
                    autoData.config.capture.windowCapture.height = (int) heightSpinnerModel.getValue();
                    autoData.setConfigChanged(true);
                }
            }
        });
    }

    public void loadSettings() {
        loading = true;
        List<String> windowTitles = new ArrayList<>();
        List<DesktopWindow> windows = WindowUtils.getAllWindows(true);
        for (DesktopWindow window : windows) {
            if (!window.getTitle().equals("")) {
                windowTitles.add(window.getTitle());
            }
        }

        String[] windowTitleArray = windowTitles.toArray(new String[0]);
        DefaultComboBoxModel<String> windowTitleComboBoxModel = new DefaultComboBoxModel<>(windowTitleArray);
        windowTitleComboBox.setModel(windowTitleComboBoxModel);
        for (String windowTitle : windowTitleArray) {
            if (windowTitle.contains(autoData.config.capture.windowCapture.windowTitle)) {
                windowTitleComboBoxModel.setSelectedItem(windowTitle);
            }
        }


        xSpinnerModel.setValue(autoData.config.capture.windowCapture.x);
        ySpinnerModel.setValue(autoData.config.capture.windowCapture.y);
        widthSpinnerModel.setValue(autoData.config.capture.windowCapture.width);
        heightSpinnerModel.setValue(autoData.config.capture.windowCapture.height);
        loading = false;
    }
}
