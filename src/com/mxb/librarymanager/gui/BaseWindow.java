package com.mxb.librarymanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BaseWindow {
    private JFrame frame;
    protected BaseWindow father;

    public BaseWindow(BaseWindow father, String title, int width, int height) {
        this.father = father;

        final int desktopWidth  = Toolkit.getDefaultToolkit().getScreenSize().width;
        final int desktopHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        frame = new JFrame("图书管理系统 - " + title);
        frame.setBounds((desktopWidth - width) / 2,(desktopHeight - height) / 2, width, height);

        // 当窗口关闭时，摧毁自己，并将父窗口设置为可用
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                destroy();
            }
        });
    }

    public void setPanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.setVisible(true);
        if (father != null)
            father.setEnabled(false);
    }

    public void subWindowDestroyed() {

    }

    public JFrame getFrame() {
        return frame;
    }

    public void destroy() {
        if (father != null) {
            father.setEnabled(true);
            frame.dispose();
            father.subWindowDestroyed();
        } else
            frame.dispose();
    }

    public void setEnabled(boolean b) {
        frame.setEnabled(b);
    }

    public void log(String logString) {
        System.out.println("界面信息：" + logString);
    }
}
