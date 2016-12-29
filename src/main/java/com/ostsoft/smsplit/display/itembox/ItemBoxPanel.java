package com.ostsoft.smsplit.display.itembox;

import com.ostsoft.smsplit.AutoData;
import com.ostsoft.smsplit.display.ImagePanel;
import com.ostsoft.smsplit.display.panel.AutoPanel;
import com.ostsoft.smsplit.display.panel.PanelData;
import com.ostsoft.smsplit.display.panel.PanelEvent;
import com.ostsoft.smsplit.observer.EventType;
import com.ostsoft.smsplit.observer.Observer;
import com.ostsoft.smsplit.observer.command.MoveRectangleCommand;
import com.ostsoft.smsplit.xml.config.ItemBox;
import com.ostsoft.smsplit.xml.config.RectangleXML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemBoxPanel extends AutoPanel {
    private final Observer observer;
    private final ItemBoxList itemBoxList = new ItemBoxList(autoData);
    private ImagePanel imagePanel = new ImagePanel() {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (imagePanel.getImage() == null) {
                return;
            }
            double percentWidth = imagePanel.getImage().getWidth(null) / 100d;
            double percentHeight = imagePanel.getImage().getHeight(null) / 100d;

            if (autoData.getSelectedItemBox() != null) {
                drawItemBox(g, percentWidth, percentHeight, autoData.getSelectedItemBox());
            }
        }

        private void drawItemBox(Graphics g, double percentWidth, double percentHeight, ItemBox itemBox) {
            for (RectangleXML rectangle : itemBox.rectangles) {
                if (rectangle.invert) {
                    g.setColor(Color.CYAN);
                } else {
                    g.setColor(Color.YELLOW);
                }

                if (rectangle.matching) {
                    g.setColor(Color.GREEN);
                }

                if (autoData.getSelectedItemBoxRectangles().contains(rectangle)) {
                    g.setColor(Color.BLUE);
                }

                g.fillRect((int) (percentWidth * rectangle.x), (int) (percentHeight * rectangle.y), (int) (percentWidth * rectangle.width), (int) (percentHeight * rectangle.height));
            }
        }
    };


    public ItemBoxPanel(AutoData autoData) {
        super(autoData, new PanelData(autoData));
        setLayout(new BorderLayout(0, 0));
        this.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(imagePanel), new JScrollPane(itemBoxList)), BorderLayout.CENTER);

        observer = (eventType, message) -> {
            switch (eventType) {
                case UPDATED_GAME_IMAGE:
                case ITEMBOX_SELECTED:
                case ITEMBOX_RECTANGLE_SELECTED:
                case ITEMBOX_RECTANGLE_MOVED:
                    if (autoData.getGameImage() != null) {
                        imagePanel.setImage(autoData.getGameImage());
                    }
                    break;
            }
        };
        autoData.addObserver(observer);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            private RectangleXML dragEntry = null;
            private double startDragX = 0;
            private double startDragY = 0;
            private double clickOffsetX = 0;
            private double clickOffsetY = 0;
            private int offsetX = 0;
            private int offsetY = 0;
            private Map<RectangleXML, Point.Double> startEntry = new HashMap<>();

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragEntry == null) {
                    return;
                }
                if (imagePanel.getImage() == null) {
                    return;
                }

                double x = ((e.getX() - offsetX) * 100d) / imagePanel.getImage().getWidth(null);
                double y = ((e.getY() - offsetY) * 100d) / imagePanel.getImage().getHeight(null);

                dragEntry.x = (x + clickOffsetX);
                dragEntry.y = (y + clickOffsetY);

                for (RectangleXML rectangleXML : autoData.getSelectedItemBoxRectangles()) {
                    Point.Double startingPosition = startEntry.get(rectangleXML);
                    rectangleXML.x = startingPosition.x + (x + clickOffsetX) - startDragX;
                    rectangleXML.y = startingPosition.y + (y + clickOffsetY) - startDragY;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (imagePanel.getImage() == null) {
                    return;
                }
                if (autoData.getSelectedItemBox() == null) {
                    return;
                }

                double x = ((e.getX() - offsetX) * 100d) / imagePanel.getImage().getWidth(null);
                double y = ((e.getY() - offsetY) * 100d) / imagePanel.getImage().getHeight(null);

                for (RectangleXML rectangleXML : autoData.getSelectedItemBox().rectangles) {
                    if (x >= rectangleXML.x && x <= rectangleXML.x + rectangleXML.width && y >= rectangleXML.y && y <= rectangleXML.y + rectangleXML.height) {
                        clickOffsetX = (rectangleXML.x - x);
                        clickOffsetY = (rectangleXML.y - y);
                        startDragX = rectangleXML.x;
                        startDragY = rectangleXML.y;
                        dragEntry = rectangleXML;

                        if (!autoData.getSelectedItemBoxRectangles().contains(rectangleXML)) {
                            if (e.isShiftDown()) {
                                autoData.getSelectedItemBoxRectangles().add(rectangleXML);
                                autoData.fireEvent(EventType.ITEMBOX_RECTANGLE_SELECTED);
                            } else {
                                startEntry.clear();
                                autoData.getSelectedItemBoxRectangles().clear();
                                autoData.getSelectedItemBoxRectangles().add(rectangleXML);
                                autoData.fireEvent(EventType.ITEMBOX_RECTANGLE_SELECTED);
                            }
                        }

                        for (RectangleXML selectedRectangle : autoData.getSelectedItemBoxRectangles()) {
                            startEntry.put(selectedRectangle, new Point.Double(selectedRectangle.x, selectedRectangle.y));
                        }
                        return;
                    }
                }

                startEntry.clear();
                autoData.getSelectedItemBoxRectangles().clear();
                autoData.fireEvent(EventType.ITEMBOX_RECTANGLE_SELECTED);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (autoData.getSelectedItemBoxRectangles().isEmpty() || dragEntry == null) {
                    return;
                }
                if (imagePanel.getImage() == null) {
                    return;
                }

                for (RectangleXML rectangleXML : autoData.getSelectedItemBoxRectangles()) {
                    Point.Double startingPosition = startEntry.get(rectangleXML);
                    rectangleXML.x = startingPosition.x;
                    rectangleXML.y = startingPosition.y;
                }

                dragEntry.x = startDragX;
                dragEntry.y = startDragY;
                double x = ((e.getX() - offsetX) * 100d) / imagePanel.getImage().getWidth(null);
                double y = ((e.getY() - offsetY) * 100d) / imagePanel.getImage().getHeight(null);

                UUID uuid = UUID.randomUUID();
                for (RectangleXML rectangleXML : autoData.getSelectedItemBoxRectangles()) {
                    autoData.getCommandCenter().executeCommand(new MoveRectangleCommand(uuid, autoData, rectangleXML
                            , rectangleXML.x + (x - startDragX) + clickOffsetX
                            , rectangleXML.y + (y - startDragY) + clickOffsetY
                    ));
                }
                dragEntry = null;
                startEntry.clear();
            }
        };
        imagePanel.addMouseListener(mouseAdapter);
        imagePanel.addMouseMotionListener(mouseAdapter);


        ItemBox[] itemBoxes = autoData.config.itemBoxes.itemBox.toArray(new ItemBox[0]);
        JComboBox<ItemBox> itemBoxXMLJComboBox = new JComboBox<>(itemBoxes);
        itemBoxXMLJComboBox.addActionListener(e -> {
            Object selectedItem = itemBoxXMLJComboBox.getModel().getSelectedItem();
            if (selectedItem instanceof ItemBox) {
                autoData.setSelectedItemBox((ItemBox) selectedItem);
                autoData.fireEvent(EventType.ITEMBOX_SELECTED);
            }
        });
        if (itemBoxes.length > 0) {
            itemBoxXMLJComboBox.setSelectedIndex(0);
        }
        add(itemBoxXMLJComboBox, BorderLayout.NORTH);


    }

    @Override
    public void handlePanelEvent(PanelEvent panelEvent) {
        super.handlePanelEvent(panelEvent);
        if (panelEvent == PanelEvent.CLOSEPANEL) {
            autoData.removeObserver(observer);
        }
    }
}

