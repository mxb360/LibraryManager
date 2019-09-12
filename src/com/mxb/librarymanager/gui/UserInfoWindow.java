package com.mxb.librarymanager.gui;

import com.mxb.librarymanager.dataprocess.UserInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInfoWindow extends BaseWindow {
    private JPanel panel1;
    private JTextField nameTextField;
    private JTextField passwordTextField;
    private JRadioButton studentRadioButton;
    private JRadioButton adminRadioButton;
    private JRadioButton canLendRadioButton;
    private JRadioButton canNotLendRadioButton;
    private JButton cancelButton;
    private JLabel titleLabel;
    private JTextField collegeTextField;
    private JButton okButton;
    private JTextField numberTextField;
    private JLabel lendCountLabel;
    private JLabel totalPriceLabel;
    private JLabel overCountLabel;
    private JLabel overPriceLabel;
    private JButton detailsButton;
    private JButton lendButton;
    private JButton returnButton;
    private JButton changeButton;
    private JButton removeButton;
    private JButton refreshButton;

    public UserInfoWindow(BaseWindow father, String cmd) {
        super(father, "用户信息 - " + cmd, 800, 700);
        titleLabel.setText("用户信息 - " + cmd);
        setPanel(panel1);

        studentRadioButton.setSelected(true);
        adminRadioButton.setSelected(false);
        canLendRadioButton.setSelected(true);
        canNotLendRadioButton.setSelected(false);
        if (!cmd.equals("显示"))
            okButton.setText(cmd);

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

        studentRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (studentRadioButton.isSelected())
                    adminRadioButton.setSelected(false);
                else
                    adminRadioButton.setSelected(true);
            }
        });
        adminRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (adminRadioButton.isSelected())
                    studentRadioButton.setSelected(false);
                else
                    studentRadioButton.setSelected(true);
            }
        });

        canNotLendRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canNotLendRadioButton.isSelected())
                    canLendRadioButton.setSelected(false);
                else
                    canLendRadioButton.setSelected(true);
            }
        });
        canLendRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canLendRadioButton.isSelected())
                    canNotLendRadioButton.setSelected(false);
                else
                    canNotLendRadioButton.setSelected(true);
            }
        });
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                details();
            }
        });
        lendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLendButton();
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onReturnButton();
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             onChangeButton();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemoveButton();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
    }

    public void onOk() {
        log("确定");
        destroy();
    }

    public void onCancel() {
        log("取消");
        destroy();
    }

    public void onLendButton() {
        log("用户借书");
    }

    public void onReturnButton() {
        log("用户还书");
    }

    public void onChangeButton() {
        log("修改用户信息");
    }

    public void onRemoveButton() {
        log("删除用户");
    }

    public void details() {
        log("详细信息");
    }

    public void refresh() {
        log("刷新信息");
    }

    public void setOkEnabled(boolean b) {
        okButton.setEnabled(b);
    }

    public void setCancelEnabled(boolean b) {
        cancelButton.setEnabled(b);
    }


    public void setNumberDisabled() {
        numberTextField.setEditable(false);
    }

    public void setAllFieldDisabled() {
        numberTextField.setEditable(false);
        nameTextField.setEditable(false);
        passwordTextField.setEditable(false);
        collegeTextField.setEditable(false);
        studentRadioButton.setEnabled(false);
        adminRadioButton.setEnabled(false);
        canNotLendRadioButton.setEnabled(false);
        canLendRadioButton.setEnabled(false);
    }

    public void setLendInfoDisVisible() {
        lendCountLabel.setVisible(false);
        totalPriceLabel.setVisible(false);
        overCountLabel.setVisible(false);
        overPriceLabel.setVisible(false);
        detailsButton.setVisible(false);
    }

    public void setManageButtonDisVisible() {
        lendButton.setVisible(false);
        returnButton.setVisible(false);
        changeButton.setVisible(false);
        removeButton.setVisible(false);
        refreshButton.setVisible(false);
    }

    @Override
    public void log(String logString) {
        super.log("用户信息：" + logString);
    }

    public void setUserInfo(UserInfo user) {
        numberTextField.setText(user.number);
        nameTextField.setText(user.name);
        numberTextField.setText(user.number);
        passwordTextField.setText(user.password);
        adminRadioButton.setSelected(user.isAdmin);
        studentRadioButton.setSelected(!user.isAdmin);
        canLendRadioButton.setSelected(user.canLend);
        canNotLendRadioButton.setSelected(!user.canLend);
        collegeTextField.setText(user.college);
    }

    public UserInfo getUserInfo() {
        UserInfo user = new UserInfo();
        user.name = nameTextField.getText().trim();
        user.password = passwordTextField.getText().trim();
        user.number = numberTextField.getText().trim();
        user.college = collegeTextField.getText().trim();
        user.isAdmin = adminRadioButton.isSelected();
        user.canLend = canLendRadioButton.isSelected();
        return user;
    }
}
