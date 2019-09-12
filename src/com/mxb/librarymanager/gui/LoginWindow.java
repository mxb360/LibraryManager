package com.mxb.librarymanager.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends BaseWindow {
    private JPanel panel1;
    private JTextField numberTextField;
    private JPasswordField passwordField;
    private JRadioButton studentLoginRadioButton;
    private JRadioButton adminLoginRadioButton;
    private JButton loginButton;
    private JCheckBox autoLoginCheckBox;
    private JCheckBox rememberPasswordCheckBox;
    private JLabel numberLabel;

    private boolean rememberPassword = false;
    private boolean autoLogin = false;
    private boolean isAdmin;
    private final String studentNumberString = "借阅证号";
    private final String adminNumberString =  "管理账号";

    public LoginWindow(BaseWindow father) {
        super(father, "登录", 500, 500);
        setPanel(panel1);

        numberLabel.setText(isAdmin ? adminNumberString : studentNumberString);
        adminLoginRadioButton.setSelected(isAdmin);
        studentLoginRadioButton.setSelected(!isAdmin);

        studentLoginRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (studentLoginRadioButton.isSelected())
                    adminLoginRadioButton.setSelected(false);
                else
                    adminLoginRadioButton.setSelected(true);
                isAdmin = adminLoginRadioButton.isSelected();
                numberLabel.setText(isAdmin ? adminNumberString : studentNumberString);
            }
        });
        adminLoginRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (adminLoginRadioButton.isSelected())
                    studentLoginRadioButton.setSelected(false);
                else
                    studentLoginRadioButton.setSelected(true);
                isAdmin = adminLoginRadioButton.isSelected();
                numberLabel.setText(isAdmin ? adminNumberString : studentNumberString);
            }
        });

        rememberPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rememberPassword = rememberPasswordCheckBox.isSelected();
                if (!rememberPassword) {
                    autoLogin = false;
                    autoLoginCheckBox.setSelected(false);
                    autoLoginChecked(false);
                }
                rememberPasswordChecked(rememberPassword);
            }
        });
        autoLoginCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoLogin = autoLoginCheckBox.isSelected();

                if (autoLogin) {
                    rememberPassword = true;
                    rememberPasswordCheckBox.setSelected(true);
                    rememberPasswordChecked(true);
                }
                autoLoginChecked(autoLogin);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAdmin = adminLoginRadioButton.isSelected();
                String number = numberTextField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (number.equals("") || password.equals("")) {
                    String numberMessage = isAdmin ? adminNumberString : studentNumberString;
                    new MessageBox.errorBox(LoginWindow.this, numberMessage + "或密码不能为空");
                    return;
                }

                log("--------- 登录 --------");
                log("借阅证号：  " + number);
                log("密码：      " + password);
                log("是否是管理员 " + (isAdmin ? "是" : "否"));

                String loginString = login(number, password, isAdmin);
                if (loginString.equals("ok")) {
                    log("登录成功");
                    destroy();

                } else {
                    new MessageBox.errorBox(LoginWindow.this, "登录失败：" + loginString);
                    log("登录失败：" + loginString);
                }
            }
        });
    }


    public void setNumberAndPassword(String number, String password) {
        numberTextField.setText(number);
        passwordField.setText(password);
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        adminLoginRadioButton.setSelected(isAdmin);
        studentLoginRadioButton.setSelected(!isAdmin);
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
        autoLoginCheckBox.setSelected(autoLogin);
    }

    public void setRememberPassword(boolean rememberPassword) {
        this.rememberPassword = rememberPassword;
        rememberPasswordCheckBox.setSelected(rememberPassword);
    }

    public void setLoginEnabled(boolean b) {
        loginButton.setEnabled(b);
    }

    public String login(String number, String password, boolean isAdmin) {
        return "账号或者密码错误";
        //return "ok";
    }

    public void rememberPasswordChecked(boolean remember) {
        log("记住密码：" + (remember ? "是" : "否"));
    }

    public void autoLoginChecked(boolean auto) {
        log("自动登录：" + (auto ? "是" : "否"));
    }

    public void log(String logString) {
        super.log("登录界面：" + logString);
    }
}
