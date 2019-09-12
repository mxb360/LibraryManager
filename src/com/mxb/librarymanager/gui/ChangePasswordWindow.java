package com.mxb.librarymanager.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordWindow extends BaseWindow {
    private JPanel panel1;
    private JButton cancelButton;
    private JButton okButton;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ChangePasswordWindow(BaseWindow father) {
        super(father, "修改密码", 400, 500);
        setPanel(panel1);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    public void onOk() {
        destroy();
        log("确定");
    }

    public void onCancel() {
        destroy();
        log("取消");
    }

    public String getOldPassword() {
        return new String(oldPasswordField.getPassword()).trim();
    }

    public String getNewPassword() {
        return new String(newPasswordField.getPassword()).trim();
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword()).trim();
    }

    @Override
    public void log(String logString) {
        super.log("修改密码：" + logString);
    }

    public static void main(String[] args) {
        new ChangePasswordWindow(null);
    }


}
