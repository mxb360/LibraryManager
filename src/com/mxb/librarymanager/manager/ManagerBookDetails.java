package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.BookInfo;
import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.BookDetailsWindow;

public class ManagerBookDetails extends BookDetailsWindow {

    BookInfo book;
    UserInfo user;

    public ManagerBookDetails(BaseWindow father, BookInfo book, UserInfo user) {
        super(father);
        setBookInfo(book);

        this.book = book;
        this.user = user;
    }

    @Override
    public void lendBook() {
        super.lendBook();
        new ManagerLendBook(father, null, user, book, ManagerLendBook.DoLendBook);
    }
}
