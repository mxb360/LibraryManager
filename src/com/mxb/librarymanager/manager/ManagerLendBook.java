package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.BookInfo;
import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.LendBookWindow;

public class ManagerLendBook extends LendBookWindow {
    public static final int DoLendBook = 0;
    public static final int DoReturnBook = 1;
    public static final int DoShowInfo = 2;

    private static final String[] cmd = {"借", "还", "还"};

    private int doWhat;
    private UserInfo admin, user;
    private BookInfo book;

    public ManagerLendBook(BaseWindow father, UserInfo admin, UserInfo user, BookInfo book, int doWat) {
        super(father, cmd[doWat]);

        this.doWhat = doWat;
        this.admin = admin;
        this.user = user;
        this.book = book;

        setInfo(user, book, admin == null ? 0 : 1);
    }
}
