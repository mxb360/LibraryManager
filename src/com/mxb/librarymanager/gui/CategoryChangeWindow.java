package com.mxb.librarymanager.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryChangeWindow extends BaseWindow {
    private JPanel panel1;
    private JComboBox categoryComboBox;
    private JTextField categoryTextField;
    private JButton cancelButton;
    private JButton cmdButton;
    private JLabel titleLabel;
    private JLabel firstLineLabel;
    private JLabel secondLineLabel;
    private JLabel noteLabel;

    private String title;

    public CategoryChangeWindow(BaseWindow father, String title) {
        super(father, "图书类型 - " + title, 500, 300);
        setPanel(panel1);

        titleLabel.setText("图书类别" + title);
        cmdButton.setText(title);
        this.title = title;

        cmdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCmd(categoryTextField.getText().trim(), categoryComboBox.getSelectedIndex());
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });
    }

    public void onCmd(String textString, int index) {
        log(title + "：string: " + textString + "  index: " + index);
    }

    public void setCategory(String[] category, int defaultIndex) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(category);
        model.setSelectedItem(category[defaultIndex]);
        categoryComboBox.setModel(model);
    }

    public void setDisabledLine(int line) {
        if (line == 0) {
            firstLineLabel.setVisible(false);
            categoryComboBox.setVisible(false);
        } else {
            secondLineLabel.setVisible(false);
            categoryTextField.setVisible(false);
        }
    }

    public void setFirstLineWords(String words) {
        firstLineLabel.setText(words);
    }

    public void setSecondLineWords(String words) {
        secondLineLabel.setText(words);
    }

    public void setNoteLabelVisible(boolean b) {
        noteLabel.setVisible(b);
    }

    @Override
    public void log(String logString) {
        super.log("图书类别" + title + "：" + logString);
    }

    public static void main(String[] args) {
        new CategoryChangeWindow(null, "修改");
    }
}
