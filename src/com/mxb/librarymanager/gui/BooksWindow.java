package com.mxb.librarymanager.gui;

import com.mxb.librarymanager.dataprocess.BookInfo;
import com.mxb.librarymanager.dataprocess.CategoryInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

class BookLabel extends JLabel {
    public BookInfo book;
    public ImageIcon image;
    public String text = "";

    private String getLine(String string) {
        return string.length() <= 15 ? string : string.substring(0, 14) + "...";
    }

    public BookLabel(BookInfo book) {
        this.book = book;

        try {
            image = new ImageIcon(new URL(book.imageUrl));
            image.setImage(image.getImage().getScaledInstance(130, 200, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            image = new ImageIcon();
        }
        text = "<html>" +
                "ISBN：" + book.isbn + "<br>" +
                getLine("书名：" + book.name) + "<br>" +
                getLine("作者：" + book.author) + "<br>" +
                getLine("类别：" + CategoryInfo.toUI(book.category)) + "<br>" +
                "页数：" + book.page + "页<br>" +
                "定价：" + book.price + "元<br>" +
                getLine("出版社：") + book.press + "<br>" +
                "库存："+ book.storageCount + "本<br>" +
                "外借：" + book.lendCount + "本<br>" +
                "</html>";

        setFont(new Font("微软雅黑", Font.PLAIN, 16));
        setForeground(Color.blue);
    }
}

public class BooksWindow extends BaseWindow {
    private JPanel panel1;
    private JTextField searchTextField;
    private JButton cancelButton;
    private JComboBox categoryComboBox;
    private JComboBox searchByComboBox;
    private JButton searchButton;
    private JList bookList;

    private DefaultListModel mode;


    public BooksWindow(BaseWindow father) {
        super(father, "所有图书", 1400, 900);
        setPanel(panel1);
        bookList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        bookList.setFixedCellHeight(225);

        bookList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                //Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                BookLabel bookLabel = (BookLabel)value;
                bookLabel.setIcon(bookLabel.image);
                bookLabel.setText(bookLabel.text);
                return bookLabel;
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchTextField.getText().trim(), categoryComboBox.getSelectedIndex(), searchByComboBox.getSelectedIndex());
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });
        bookList.addComponentListener(new ComponentAdapter() {
        });
        bookList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                details(((BookLabel)bookList.getSelectedValue()).book);
            }
        });
    }

    public void setBookInfos(ArrayList<BookInfo> books) {
        mode = new DefaultListModel();
        for (int i = 0; i < books.size(); i++) {
            mode.add(i, new BookLabel(books.get(i)));
        }
        bookList.setModel(mode);
        bookList.setPreferredSize(new Dimension(800, 225 * (books.size() / 3 + 1)));
    }

    public void setCategory(String[] category) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(category);
        categoryComboBox.setModel(model);
    }

    public void search(String words, int category, int searchBy) {
        log("搜索：" + words + "  " + category + "  " + searchBy);
    }

    public void details(BookInfo book) {
        log("bookInfo.isbn=" + book.isbn + "  book.name=" + book.name);
    }

    public void setSearchEnabled(boolean b) {
        searchButton.setVisible(b);
    }

    @Override
    public void log(String logString) {
        super.log("所有图书界面：" + logString);
    }
}
