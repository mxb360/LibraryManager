package com.mxb.librarymanager.gui;

import com.mxb.librarymanager.dataprocess.BookInfo;
import com.mxb.librarymanager.dataprocess.CategoryInfo;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class BookChangeWindow extends BaseWindow {
    private JPanel panel1;
    private JTextField isbnTextField;
    private JTextField nameTextField;
    private JTextField authorTextField;
    private JComboBox categoryComboBox;
    private JTextField priceTextField;
    private JTextField pressTextField;
    private JTextField countTextField;
    private JTextField lendCountTextField;
    private JTextArea desTextArea;
    private JButton importImageButton;
    private JLabel imageLabel;
    private JButton cmdButton;
    private JButton cancelButton;
    private JLabel titleLabel;
    private JPanel imagePane;
    private JButton loadButton;
    private JTextField pageTextField;

    private String fileName = "";
    private JFileChooser fileChooser1, fileChooser2;
    private String cmd;

    public BookChangeWindow(BaseWindow father, String cmd) {
        super(father, cmd + "图书信息", 900, 800);
        setPanel(panel1);

        titleLabel.setText(cmd + "图书信息");
        cmdButton.setText(cmd);
        this.cmd = cmd;

        desTextArea.setLineWrap(true);
        fileChooser1 = new JFileChooser(".");
        fileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser1.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                String name = f.getName();
                return f.isDirectory() || name.endsWith(".png") ||  name.endsWith(".jpg");
            }
            @Override
            public String getDescription() {
                return "图片文件(*.jpg, *.png)";
            }
        });
        fileChooser2 = new JFileChooser(".");
        fileChooser2.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser2.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                String name = f.getName();
                return f.isDirectory() || name.endsWith(".txt");
            }
            @Override
            public String getDescription() {
                return "文本文件";
            }
        });

        importImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = fileChooser1.showOpenDialog(getFrame());
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser1.getSelectedFile();
                    fileName = file.getPath();
                    ImageIcon image = new ImageIcon(fileName);
                    imageLabel.setIcon(image);
                    image.setImage(image.getImage().getScaledInstance(imagePane.getWidth(),
                            imagePane.getHeight(), Image.SCALE_DEFAULT ));
                    log(fileName);
                } else {
                    //fileName = "";
                    //imageLabel.setIcon(new ImageIcon());
                }
            }
        });
        cmdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCmd();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName2;
                int res = fileChooser2.showOpenDialog(getFrame());
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser2.getSelectedFile();
                    fileName2 = file.getPath();
                    log(fileName2);
                    parseTextInfo(fileName2);
                }
            }
        });
    }

    public void onCmd() {
        log("<cmd>: " + cmd);
    }

    public void setCategory(ArrayList<CategoryInfo> categoryInfos) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (CategoryInfo categoryInfo: categoryInfos)
            model.addElement(categoryInfo.toUI());
        model.setSelectedItem(categoryInfos.get(1).name);
        categoryComboBox.setModel(model);
    }

    public void setInfo(BookInfo info) {
        isbnTextField.setText(info.isbn);
        nameTextField.setText(info.name);
        authorTextField.setText(info.author);
        categoryComboBox.setSelectedItem(info.category);
        priceTextField.setText(info.price);
        pressTextField.setText(info.press);
        countTextField.setText(info.storageCount + "");
        lendCountTextField.setText(info.lendCount + "");
        desTextArea.setText(info.des);
        pageTextField.setText(info.page + "");

        log("imgurl: " + info.imageUrl);
        try {
            ImageIcon image;
            if (info.imageUrl.startsWith("http://"))
                image = new ImageIcon(new URL(info.imageUrl));
            else {
                image = new ImageIcon(info.imageUrl);
                setFileName(info.imageUrl);
            }
            imageLabel.setIcon(image);
            image.setImage(image.getImage().getScaledInstance(imagePane.getWidth(),
                    imagePane.getHeight(), Image.SCALE_DEFAULT ));
        } catch (Exception e) {
            log("图片加载失败：" + e.getMessage());
        }
    }

    public void parseTextInfo(String fileName) {

    }

    public BookInfo getInfo() {
        BookInfo info = new BookInfo();
        info.isbn = isbnTextField.getText().trim();
        info.name = nameTextField.getText().trim();
        info.author = authorTextField.getText().trim();
        info.category = (String)categoryComboBox.getSelectedItem();
        info.category = info.category.equals(CategoryInfo.NULL_CATEGORY_NAME) ? "" : info.category;
        info.price = priceTextField.getText().trim();
        info.des = desTextArea.getText();
        info.press = pressTextField.getText().trim();
        info.imageUrl = getFileName();
        try {
            info.storageCount = Integer.parseInt(countTextField.getText().trim());
            info.lendCount = Integer.parseInt(lendCountTextField.getText().trim());
            info.page = Integer.parseInt(pageTextField.getText().trim());
        } catch (NumberFormatException e) {
            return null;
        }

        return info;
    }

    public void setIsbnDisable() {
        isbnTextField.setEditable(false);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setAllDisable() {
        isbnTextField.setEditable(false);
        nameTextField.setEditable(false);
        pressTextField.setEditable(false);
        categoryComboBox.setEnabled(false);
        priceTextField.setEditable(false);
        lendCountTextField.setEditable(false);
        desTextArea.setEditable(false);
        importImageButton.setEnabled(false);
        authorTextField.setEditable(false);
        countTextField.setEditable(false);
        loadButton.setEnabled(false);
        pageTextField.setEditable(false);
    }

    @Override
    public void log(String logString) {
        super.log(cmd + "图书信息界面：" + logString);
    }

    public static void main(String[] args) {
        new BookChangeWindow(null, "添加");
    }
}
