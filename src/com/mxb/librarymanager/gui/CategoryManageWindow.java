package com.mxb.librarymanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryManageWindow extends BaseWindow {
    private JPanel panel1;
    private JList categoryList;
    private JButton returnButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton changeButton;
    private JLabel promptLabel;

    public CategoryManageWindow(BaseWindow father) {
        super(father, "图书类别管理", 600, 500);
        setPanel(panel1);

        categoryList.setCellRenderer(new DefaultListCellRenderer() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemove(categoryList.getSelectedIndex());
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onChange(categoryList.getSelectedIndex());
            }
        });
    }

    public void onAdd() {
        log("添加类别");
    }

    public void onChange(int index) {
        log("修改类别: " + index);
    }

    public void onRemove(int index) {
        log("移除类别: " + index);
    }

    @Override
    public void log(String logString) {
        super.log("图书类别管理：" + logString);
    }

    public void setCategory(String[] category) {
        promptLabel.setText("共有" + category.length + "个图书类别：");
        DefaultListModel model = new DefaultListModel();
        for (String c : category)
            model.addElement(c);
        categoryList.setModel(model);
    }
}
