package com.mxb.librarymanager.gui;

import com.mxb.librarymanager.dataprocess.UserInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageWindow extends BaseWindow {
    private JPanel panel1;
    private JButton returnBookButton;
    private JButton changePasswordButton;
    private JButton LogoutButton;
    private JButton detailsButton;
    private JLabel nameLabel;
    private JLabel numberLabel;
    private JLabel idLabel;
    private JLabel canLendLabel;
    private JLabel lendCountLabel;
    private JLabel totalPriceLabel;
    private JLabel overCountLabel;
    private JLabel overPriceLabel;
    private JButton allBookButton;
    private JButton lendBookButton;
    private JButton addBookButton;
    private JButton changeBookButton;
    private JButton removeBookButton;
    private JButton categoryManageButton;
    private JButton addUserButton;
    private JButton changeUserInfoButton;
    private JButton userInfoButton;
    private JButton removeUserButton;
    private JLabel collegeLabel;
    private JButton aboutButton;
    private JButton lendInfoButton;
    private JButton quitButton;

    private boolean isAdmin;

    public ManageWindow(BaseWindow father, boolean isAdmin) {
        super(father, "管理界面", 1200, 800);
        setPanel(panel1);
        this.isAdmin = isAdmin;

        if (!isAdmin) {
            addBookButton.setEnabled(false);
            changeBookButton.setEnabled(false);
            removeBookButton.setEnabled(false);
            categoryManageButton.setEnabled(false);
            addUserButton.setEnabled(false);
            changeUserInfoButton.setEnabled(false);
            userInfoButton.setEnabled(false);
        }

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        LogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                learnDetails();
            }
        });
        allBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allBook();
            }
        });
        lendInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lendInfo();
            }
        });
        lendBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lendBook();
            }
        });
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        changeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeBook();
            }
        });
        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });
        categoryManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryManage();
            }
        });
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        changeUserInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeUserInfo();
            }
        });
        userInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getUserInfo();
            }
        });
        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeUser();
            }
        });
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                about();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });
    }

    public void changePassword() {
        log("修改密码");
    }

    public void logout() {
        log("退出登录");
    }

    public void about() {
        log("关于软件");
    }

    public void learnDetails() {
        log("了解详情");
    }

    public void allBook() {
        log("所有图书");
    }

    public void lendInfo() {
        log("借书信息");
    }

    public void lendBook() {
        log("图书借阅");
    }

    public void returnBook() {
        log("图书归还");
    }

    public void addBook() {
        log("添加图书");
    }

    public void changeBook() {
        log("修改图书");
    }

    public void removeBook() {
        log("移除图书");
    }

    public void categoryManage() {
        log("类别管理");
    }
    public void addUser() {
        log("添加用户");
    }
    public void changeUserInfo() {
        log("修改用户");
    }
    public void getUserInfo() {
        log("用户信息");
    }
    public void removeUser() {
        log("移除用户");
    }

    private String setStyle(String string) {
        return string;
    }

    public void setUserInfo(UserInfo user) {
        nameLabel.setText((isAdmin ? "用户名：" : "姓名：") + setStyle(user.name));
        numberLabel.setText((isAdmin ? "管理账号：" : "借阅证号：") + setStyle(user.number));
        collegeLabel.setText("学院：" + setStyle(user.college));
        idLabel.setText("身份：" + setStyle(isAdmin ? "管理员" : "学生"));
        canLendLabel.setText("借阅许可：" + setStyle((user.canLend ? "" : "不") + "可以借书"));
    }

    public void setLendInfo(int lendCount, String totalPrice, int overCount, String overPrice) {
        lendCountLabel.setText("当前借书：" + setStyle("" + lendCount) + "本");
        totalPriceLabel.setText("借书总价值：" + setStyle(totalPrice) + "元");
        overCountLabel.setText("已超期：" + setStyle("" + overCount) + "本");
        overPriceLabel.setText("超期罚金：" + setStyle(overPrice) + "元");
    }
}
