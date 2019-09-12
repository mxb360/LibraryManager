package com.mxb.librarymanager.gui;

import com.mxb.librarymanager.dataprocess.BookInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class BookDetailsWindow extends BaseWindow {
    private JPanel panel1;
    private JTextArea desTextArea;
    private JButton lendBookButton;
    private JButton cancelButton;
    private JLabel imageLabel;
    private JLabel isbnLabel;
    private JLabel nameLabel;
    private JLabel authorLabel;
    private JLabel categoryLabel;
    private JLabel pageLabel;
    private JLabel priceLabel;
    private JLabel pressLabel;
    private JLabel lendCountLabel;
    private JLabel countLabel;
    private JPanel imagePane;

    public BookDetailsWindow(BaseWindow father) {
        super(father, "图书详情", 1100, 800);
        setPanel(panel1);

        desTextArea.setLineWrap(true);

        lendBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lendBook();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });
    }

    public void lendBook() {
        log("我要借书");
    }

    public void setBookInfo(BookInfo book) {
        isbnLabel.setText("ISBN号：" + book.isbn);
        nameLabel.setText("书名：" + book.name);
        authorLabel.setText("作者：" + book.author);
        categoryLabel.setText("类别：" + book.category);
        pageLabel.setText("页数：" + book.page);
        priceLabel.setText("定价：" + book.price);
        pressLabel.setText("出版社：" + book.press);
        lendCountLabel.setText("外借：" + book.lendCount);
        countLabel.setText("现存：" + book.storageCount);
        desTextArea.setText("详情介绍：" + book.des);

        try {
            ImageIcon image;
            image = new ImageIcon(new URL(book.imageUrl));
            imageLabel.setIcon(image);
            image.setImage(image.getImage().getScaledInstance(imagePane.getWidth(),
                    imagePane.getHeight(), Image.SCALE_DEFAULT ));
        } catch (Exception e) {
            log("图片加载失败：" + e.getMessage());
        }
    }

    @Override
    public void log(String logString) {
        super.log("图书详情界面：" + logString);
    }
}
