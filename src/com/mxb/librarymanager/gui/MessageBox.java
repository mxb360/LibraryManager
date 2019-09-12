package com.mxb.librarymanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MessageBox extends BaseWindow {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel messageLabel;

    public MessageBox(BaseWindow father, String title, String message) {
        this(father, title, message, Color.black);
    }

    public MessageBox(BaseWindow father, String title, String message, Color color) {
        super(father, title, 800, 250);
        setPanel(contentPane);
        messageLabel.setText(message);
        messageLabel.setForeground(color);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });
    }

    public static class messageBox extends MessageBox {
        public messageBox(BaseWindow father, String message) {
            super(father, "信息", "信息：" + message + "。", Color.green);
            log("信息：" + message + "。");
        }
    }

    public static class errorBox extends MessageBox {
        public errorBox(BaseWindow father, String message) {
            super(father, "错误", "错误：" + message + "。", Color.red);
            log("错误：" + message + "。");
        }
    }

    @Override
    public void log(String logString) {
        super.log("信息框：" + logString);
    }
}
