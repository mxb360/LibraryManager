package com.mxb.librarymanager.gui;

import com.mxb.librarymanager.dataprocess.BookInfo;
import com.mxb.librarymanager.dataprocess.UserInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class LendBookWindow extends BaseWindow {
    private JPanel panel1;
    private JTextField numberTextField;
    private JTextField userNameTextField;
    private JTextField isbnTextField;
    private JTextField bookNameTextField;
    private JTextField countTextField;
    private JTextField lendYearTextField;
    private JTextField lendMonthTextField;
    private JTextField lendDayTextField;
    private JTextField lendDaysTextField;
    private JTextField overTextField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JTextField returnYearTextField;
    private JTextField returnMonthTextField;
    private JTextField returnDayTextField;
    private JTextField totalOverTextField;
    private JComboBox bookStatusComboBox;
    private JLabel titleLabel;
    private JTextField overDayTextField;
    private JButton userButton;
    private JButton bookButton;
    private JTextField authorTextField;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JComboBox lendStatusComboBox;
    private JLabel bookStatusLabel;
    private JLabel returnDateLabel;
    private JLabel overDaysLabel;
    private JLabel totalOverLabel;
    private JLabel alreadyLabel;
    private JLabel returnDateLabel2;
    private JLabel returnDateLabel3;
    private JLabel returnDateLabel4;
    private JLabel overDaysLabel2;
    private JLabel totalOverLabel2;

    private String title;

    public LendBookWindow(BaseWindow father, String title) {
        super(father, "用户" + title + "书信息", 900, 800);
        setPanel(panel1);
        this.title = title;

        titleLabel.setText("用户" + title + "书信息");
        confirmButton.setText("确认" + title + "书");

        if (title.equals("借")) {
            returnDayTextField.setVisible(false);
            returnMonthTextField.setVisible(false);
            returnYearTextField.setVisible(false);
            returnDateLabel.setVisible(false);
            returnDateLabel2.setVisible(false);
            returnDateLabel3.setVisible(false);
            returnDateLabel4.setVisible(false);
            bookStatusComboBox.setVisible(false);
            bookStatusLabel.setVisible(false);
            overDayTextField.setVisible(false);
            overDaysLabel2.setVisible(false);
            overDaysLabel.setVisible(false);
            totalOverTextField.setVisible(false);
            totalOverLabel.setVisible(false);
            totalOverLabel2.setVisible(false);
            yesRadioButton.setVisible(false);
            noRadioButton.setVisible(false);
            alreadyLabel.setVisible(false);
            userButton.setVisible(false);
            bookButton.setVisible(false);
        }


        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userDetails();
            }
        });
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookDetails();
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });
        yesRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yesRadioButton.setSelected(true);
                noRadioButton.setSelected(false);
            }
        });
        noRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yesRadioButton.setSelected(false);
                noRadioButton.setSelected(true);
            }
        });
    }

    public void setInfo(UserInfo user, BookInfo book, int lendStatus) {
        userNameTextField.setText(user.name);
        numberTextField.setText(user.number);
        isbnTextField.setText(book.isbn);
        bookNameTextField.setText(book.name);
        authorTextField.setText(book.author);

        userNameTextField.setEditable(false);
        numberTextField.setEditable(false);
        isbnTextField.setEditable(false);
        bookNameTextField.setEditable(false);
        authorTextField.setEditable(false);

        Calendar calendar = Calendar.getInstance();
        lendYearTextField.setText("" + calendar.get(Calendar.YEAR));
        lendMonthTextField.setText("" + (calendar.get(Calendar.MONTH) + 1));
        lendDayTextField.setText("" + calendar.get(Calendar.DATE));

        countTextField.setText("1");
        countTextField.setEditable(false);
        lendStatusComboBox.setEnabled(false);
        lendStatusComboBox.setSelectedIndex(lendStatus);
    }

    public void onOk() {
        log("确认");
    }

    public void userDetails() {
        log("用户信息");
    }

    public void bookDetails() {
        log("图书信息");
    }

    @Override
    public void log(String logString) {
        super.log(title + "书界面：" + logString);
    }

    public static void main(String[] args) {
        new LendBookWindow(null, "借");
    }
}
