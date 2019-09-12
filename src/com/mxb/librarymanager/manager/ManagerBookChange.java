package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.BookInfo;
import com.mxb.librarymanager.dataprocess.CategoryInfo;
import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.BookChangeWindow;
import com.mxb.librarymanager.gui.MessageBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ManagerBookChange extends BookChangeWindow {
    public static final int DoAddBook = 0;
    public static final int DoChangeBook = 1;
    public static final int DoRemoveBook = 2;

    private static final String[] cmd = {"添加", "修改", "删除"};
    private int doWhat;
    private UserInfo admin;
    private ManagerDataProcess dataProcess = new ManagerDataProcess();
    private ArrayList<CategoryInfo> categoryInfos = new ArrayList<CategoryInfo>();
    private BookInfo book;

    public ManagerBookChange(BaseWindow father, UserInfo admin, int doWhat) {
        this(father, admin, null, doWhat);
    }

    public ManagerBookChange(BaseWindow father, UserInfo admin, BookInfo book, int doWhat) {
        super(father, cmd[doWhat]);

        this.doWhat = doWhat;
        this.admin = admin;
        this.book = book;

        String res = dataProcess.getCategoryAllInfo(admin, categoryInfos);
        if (!res.equals("ok")) {
            new MessageBox.errorBox(this, "图书类别获取失败") {
                @Override
                public void destroy() {
                    super.destroy();
                    father.destroy();
                }
            };
        } else {
            setCategory(categoryInfos);
        }

        if (doWhat != DoAddBook)
            setIsbnDisable();
        if (doWhat == DoRemoveBook)
            setAllDisable();
        if (doWhat == DoChangeBook || doWhat == DoRemoveBook)
            setInfo(book);
    }

    @Override
    public void onCmd() {
        super.onCmd();

        BookInfo book = null;
        String res = "";
        if (doWhat == DoAddBook || doWhat == DoChangeBook) {
            book = getInfo();
            if (book == null || checkInfo(book)) {
                new MessageBox.errorBox(this, "数据格式不正确或者信息不完整");
                return;
            }
        }

        if (doWhat == DoAddBook)
            res = dataProcess.addBookInfo(admin, book);
        else if (doWhat == DoChangeBook)
            res = dataProcess.changeBookInfo(admin, book);
        else if (doWhat == DoRemoveBook)
            res = dataProcess.removeBookInfo(admin, this.book);

        if (res.equals("ok")) {
            new MessageBox.messageBox(this, cmd[doWhat] + "图书信息成功");
        } else {
            new MessageBox.errorBox(this, cmd[doWhat] + "图书信息失败：" + res);
        }
    }

    @Override
    public void parseTextInfo(String fileName) {
        String res = "";
        log("解析文件：" + fileName);
        try {
            InputStream in = new FileInputStream(new File(fileName));
            byte[] bytes = new byte[20480];
            int n = in.read(bytes);
            res = new String(bytes, 0, n);
            in.close();
        } catch (IOException e) {
            log("解析文件：" + fileName + "出错");
            return;
        }

        String[] info = res.split(";");
        //if (info.length < 8) {
            log("解析文件：" + fileName + ": length = " + info.length);
            //return;
        //}

        BookInfo book = new BookInfo();
        try {
            book.isbn = info[0].substring(1).trim();
            book.name = info[1].trim();
            book.author = info[2].trim();
            book.category = info[4].trim();
            book.price = info[5].trim();
            book.press = info[6].trim();
            book.des = info[8];
            book.imageUrl = new File(fileName).getParent() + '\\' + info[9].trim();

            log("--- " + book.imageUrl);
            book.page = Integer.parseInt(info[3].trim());
            book.storageCount = Integer.parseInt(info[7].trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setInfo(book);
    }

    private boolean checkInfo(BookInfo book) {
        return book.isbn.equals("") || book.name.equals("") || book.author.equals("") ||
                book.press.equals("") || book.des.equals("");
    }
}
