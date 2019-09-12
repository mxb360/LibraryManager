package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.BookInfo;
import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.GetNumberOrIsbnWindow;
import com.mxb.librarymanager.gui.MessageBox;

public class ManagerGetInfoOrIsbn extends GetNumberOrIsbnWindow {
    static final int NextChangeUserInfo = 0;
    static final int NextRemoveUserInfo = 1;
    static final int NextChangeBookInfo = 2;
    static final int NextRemoveBookInfo = 3;
    static final int NextLendBook = 4;
    static final int NextReturnBook = 5;

    static final int DoGetUserNumber = 0;
    static final int DoGetBookIsbn = 1;
    static final int DoGetUserNumberAndBookIsbn = 2;

    private ManagerDataProcess dataProcess = new ManagerDataProcess();

    private UserInfo admin;
    private int doNext;
    private int doWhat;
    private static final String[] cmd = {"用户", "图书", "用户和图书"};

    public ManagerGetInfoOrIsbn(BaseWindow father, UserInfo admin,int doWhat, int doNext) {
        super(father, cmd[doWhat]);

        this.admin = admin;
        this.doNext = doNext;
        this.doWhat = doWhat;
        if (doWhat == DoGetUserNumber)
            setVisibleLine(1);
        else if (doWhat == DoGetBookIsbn)
            setVisibleLine(2);
    }

    @Override
    public void getUser(String number, String isbn) {
        UserInfo user = new UserInfo();
        BookInfo book = new BookInfo();

        if ((doWhat == DoGetUserNumber  || doWhat == DoGetUserNumberAndBookIsbn) && number.equals(""))
            new MessageBox.errorBox(this, "信息不完整");
        if ((doWhat == DoGetBookIsbn  || doWhat == DoGetUserNumberAndBookIsbn) && isbn.equals(""))
            new MessageBox.errorBox(this, "信息不完整");
        else {
            String res = "";
            if (doWhat == DoGetUserNumber || doWhat == DoGetUserNumberAndBookIsbn)
                res = dataProcess.getUserInfo(admin, user, number);
            if (doWhat == DoGetBookIsbn || doWhat == DoGetUserNumberAndBookIsbn)
                res = dataProcess.getBook(admin, book, isbn);

            if (res.equals("ok")) {
                if (doNext == NextChangeUserInfo)
                    new ManagerUserInfo(this, admin, user, ManagerUserInfo.DoChangeUserInfo);
                else if (doNext == NextRemoveUserInfo)
                    new ManagerUserInfo(this, admin, user, ManagerUserInfo.DoRemoveUserInfo);
                else if (doNext == NextChangeBookInfo)
                    new ManagerBookChange(this, admin, book, ManagerBookChange.DoChangeBook);
                else if (doNext == NextRemoveBookInfo)
                    new ManagerBookChange(this, admin, book, ManagerBookChange.DoRemoveBook);
                else if (doNext == NextLendBook)
                    new ManagerLendBook(this, admin, user, book, ManagerLendBook.DoLendBook);
                destroy();
            } else {
               new MessageBox.errorBox(this, "获取" + cmd[doWhat] + "信息失败：" + res);
            }
        }
    }
}
