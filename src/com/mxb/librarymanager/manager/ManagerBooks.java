package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.BookInfo;
import com.mxb.librarymanager.dataprocess.CategoryInfo;
import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.BooksWindow;
import com.mxb.librarymanager.gui.MessageBox;

import java.util.ArrayList;

public class ManagerBooks extends BooksWindow {


    private UserInfo user;
    private ManagerDataProcess dataProcess = new ManagerDataProcess();
    String[] category;

    public ManagerBooks(BaseWindow father, UserInfo user) {
        super(father);

        this.user = user;
        ArrayList<CategoryInfo> categoryInfos = new ArrayList<>();

        String res = dataProcess.getCategoryAllInfo(user, categoryInfos);
        if (!res.equals("ok")) {
            new MessageBox.errorBox(this, "获取图书类别信息失败：" + res) {
                @Override
                public void destroy() {
                    super.destroy();
                    father.destroy();
                }
            };
            return;
        }

        category = new String[categoryInfos.size() + 1];
        category[0] = "所有类别";
        int flag = 1, n = 1;
        for (int i = 1; i < categoryInfos.size() + 1; i++) {
            CategoryInfo info = categoryInfos.get(i - 1);

            if (info.isNullCategory()) {
                category[1] = info.toUI();
                flag = 0;
            } else
                category[i + flag] = info.toUI();
        }
        setCategory(category);
        category[0] = "*";

        search("", 0, 0);
    }

    @Override
    public void search(String words, int category, int searchBy) {
        setSearchEnabled(false);
        super.search(words, category, searchBy);

        ArrayList<BookInfo> books = new ArrayList<>();
        String res = dataProcess.searchBook(user, this.category[category], searchBy, words, books);
        if (res.equals("ok")) {
            setBookInfos(books);
        } else {
            new MessageBox.errorBox(this, "图书搜索失败：" + res);
        }
        setSearchEnabled(true);
    }

    @Override
    public void details(BookInfo book) {
        super.details(book);
        new ManagerBookDetails(this, book, user);
    }
}
