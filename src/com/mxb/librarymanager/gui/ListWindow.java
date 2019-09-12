package com.mxb.librarymanager.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListWindow extends BaseWindow {
    private JPanel panel1;
    private JTable infoTable;
    private JButton cancelButton;
    private JLabel titleLabel;
    private JLabel countLabel;

    private String title;

    public ListWindow(BaseWindow father, String title, Object info[][], String[] head) {
        super(father, title + "列表", 900, 700);
        setPanel(panel1);

        this.title = title;
        titleLabel.setText(title + "列表");
        DefaultTableModel model = new DefaultTableModel(info, head) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < 15 - info.length; i++)
            model.addRow(new Object[0]);
        infoTable.setModel(model);
        countLabel.setText("共有结果" + info.length + "条");

        JTableHeader header = infoTable.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));
        header.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        header.setForeground(Color.blue);
        header.setBackground(Color.cyan);
        infoTable.setRowHeight(25);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });
        infoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    int row = infoTable.getSelectedRow();
                    if (row < info.length) {
                        rowChicked(row);
                    }
                }
            }
        });
    }

    public void rowChicked(int index) {
        log("点击" + index + "行");
    }

    @Override
    public void log(String logString) {
        super.log(title + "列表：" + logString);
    }

    public static void main(String[] args) {
        String head[] = {"账号", "姓名", "学院", "管理员", "能借书"};
        Object info[][] = {
                {"123456", "马小波", "西华师范大学电子信息工程学院", "是", "是"},
                {"123", "123", "123", "否", "是"},
        };

        new ListWindow(null, "用户", info, head);
    }
}
