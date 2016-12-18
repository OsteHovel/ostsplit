package com.ostsoft.smsplit.display.itembox;

import com.ostsoft.smsplit.AutoData;
import com.ostsoft.smsplit.observer.Observer;
import com.ostsoft.smsplit.xml.config.RectangleXML;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.List;

public class ItemBoxList extends JList<ItemBoxNode> {
    private final Observer observer;
    private boolean disableSelecting = false;

    public ItemBoxList(AutoData autoData) {
        super();

        DefaultListModel<ItemBoxNode> model = new DefaultListModel<>();
        setModel(model);

        addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (disableSelecting) {
                    return;
                }

                autoData.getSelectedItemBoxRectangles().clear();
                for (ItemBoxNode itemBoxNode : getSelectedValuesList()) {
                    autoData.getSelectedItemBoxRectangles().add(itemBoxNode.getRectangle());
                }

            }
        });

        observer = (eventType, message) -> {
            switch (eventType) {
                case ITEMBOX_SELECTED:
                    model.clear();
                    for (RectangleXML rectangle : autoData.getSelectedItemBox().rectangles) {
                        ItemBoxNode itemBoxNode = new ItemBoxNode(autoData, autoData.getSelectedItemBox(), rectangle);
                        model.addElement(itemBoxNode);
                    }
                    break;
                case ITEMBOX_RECTANGLE_SELECTED:
                    List<Integer> list = new ArrayList<>();
                    for (RectangleXML rectangleXML : autoData.getSelectedItemBoxRectangles()) {
                        for (int i = 0; i < model.getSize(); i++) {
                            if (rectangleXML.equals(model.get(i).getRectangle())) {
                                list.add(i);
                                break;
                            }
                        }
                    }

                    int[] array = list.stream().mapToInt(i -> i).toArray();
                    disableSelecting = true;
                    setSelectedIndices(array);
                    disableSelecting = false;

                    break;
                case ITEMBOX_RECTANGLE_MOVED:


                    break;
            }
        };
        autoData.addObserver(observer);
    }
}
