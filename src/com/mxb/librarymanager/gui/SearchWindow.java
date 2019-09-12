package com.mxb.librarymanager.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchWindow extends BaseWindow {
    private JPanel panel1;
    private JComboBox typeComboBox;
    private JTextField searchTextField;
    private JButton SearchButton;
    private JButton cancelButton;
    private JLabel searchLabel;

    private String title;
    private boolean[] disableField;

    public SearchWindow(BaseWindow father, String name, String[] types, int[] disableFieldIndex) {
        super(father, name + "检索", 700, 250);
        setPanel(panel1);
        title = name;

        disableField = new boolean[types.length];
        if (disableFieldIndex != null) {
            for (int index : disableFieldIndex)
                disableField[index] = true;
        }

        searchLabel.setText(name + "检索      ");
        for (String item : types)
            typeComboBox.addItem(item);

        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchTextField.getText().trim(), typeComboBox.getSelectedIndex());
            }
        });
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean disableIndex = disableField[typeComboBox.getSelectedIndex()];
                searchTextField.setVisible(!disableIndex);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log("取消");
                destroy();
            }
        });
    }

    public void search(String searchString, int index) {
        log("搜索：" + searchString + " (" + index + ")");
    }

    @Override
    public void log(String logString) {
        super.log(title + "搜索：" + logString);
    }
}
