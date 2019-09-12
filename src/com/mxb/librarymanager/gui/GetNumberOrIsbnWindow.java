package com.mxb.librarymanager.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetNumberOrIsbnWindow extends BaseWindow {
    private JPanel panel1;
    private JTextField numberTextField;
    private JButton getUserInfoButton;
    private JLabel numberLabel;
    private JLabel titleLabel;
    private JTextField isbnTextField;
    private JLabel isbnLabel;

    public GetNumberOrIsbnWindow(BaseWindow father, String name) {
        super(father, "获取用户信息", 450, 300);
        setPanel(panel1);

        titleLabel.setText("获取" + name);
        getUserInfoButton.setText("获取" + name);

        getUserInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getUser(numberTextField.getText().trim(), isbnTextField.getText().trim());
            }
        });
    }

    //public void setPrompt(String prompt) {
    //    numberLabel.setText(prompt);
    //}

    public void setVisibleLine(int line) {
        if (line == 1) {
            isbnLabel.setVisible(false);
            isbnTextField.setVisible(false);
        } else if (line == 2) {
            numberLabel.setVisible(false);
            numberTextField.setVisible(false);
        }
    }

    public void getUser(String number, String isbn) {
        log("获取用户：" + number + " " + isbn);
    }

    @Override
    public void log(String logString) {
        super.log("获取用户：" + logString);
    }
}
