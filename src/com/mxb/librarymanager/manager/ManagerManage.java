package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.ManageWindow;
import com.mxb.librarymanager.gui.MessageBox;

public class ManagerManage extends ManageWindow {
    UserInfo user;
    UserInfo admin;
    ManagerDataProcess dataProcess = new ManagerDataProcess();

    public ManagerManage(BaseWindow father, UserInfo user) {
        super(father, user.isAdmin);

        String res = dataProcess.getUserInfo(user);
        if (res.equals("ok")) {
            setUserInfo(user);
            this.user = user;
            this.admin = user;
        } else
            new MessageBox.errorBox(this, "获取用户信息失败：" + res);
    }

    @Override
    public void logout() {
        super.logout();
        dataProcess.logout(user);
        new ManagerLogin(true);
        destroy();
    }

    @Override
    public void changePassword() {
        super.changePassword();
        new ManagerChangePassword(this, user);
    }

    @Override
    public void about() {
        super.about();
        new MessageBox(this, "关于软件",
                "<html>" + "图书管理系统Java客户端 V1.0 <br>" +
                "作者：马小波 <br>日期：2019-6 <br>" +
                "Copyright (C) 2019 | By mxb | All rights reserved</html>");
    }

    @Override
    public void learnDetails() {
        super.learnDetails();
        new MessageBox.errorBox(this, "对不起，该功能尚未实现");
    }

    @Override
    public void allBook() {
        super.allBook();
        new ManagerBooks(this, user);
    }

    @Override
    public void lendInfo() {
        super.lendInfo();
        new MessageBox.errorBox(this, "对不起，该功能尚未实现");
    }

    @Override
    public void lendBook() {
        super.lendBook();
        new ManagerGetInfoOrIsbn(this, admin, ManagerGetInfoOrIsbn.DoGetUserNumberAndBookIsbn, ManagerGetInfoOrIsbn.NextLendBook);
    }

    @Override
    public void returnBook() {
        super.returnBook();
        new ManagerGetInfoOrIsbn(this, admin, ManagerGetInfoOrIsbn.DoGetUserNumberAndBookIsbn, ManagerGetInfoOrIsbn.NextReturnBook);
    }

    @Override
    public void addBook() {
        super.addBook();
        new ManagerBookChange(this, admin, ManagerBookChange.DoAddBook);
    }

    @Override
    public void changeBook() {
        super.changeBook();
        new ManagerGetInfoOrIsbn(this, admin, ManagerGetInfoOrIsbn.DoGetBookIsbn, ManagerGetInfoOrIsbn.NextChangeBookInfo);
    }

    @Override
    public void removeBook() {
        super.removeBook();
        new ManagerGetInfoOrIsbn(this, admin, ManagerGetInfoOrIsbn.DoGetBookIsbn, ManagerGetInfoOrIsbn.NextRemoveBookInfo);
    }

    @Override
    public void categoryManage() {
        super.categoryManage();
        new ManagerCategoryManage(this, admin);
    }

    @Override
    public void addUser() {
        super.addUser();
        //new ManagerAddUserInfo(this, user);
        new ManagerUserInfo(this, admin, ManagerUserInfo.DoAddUserInfo);
    }

    @Override
    public void changeUserInfo() {
        super.changeUserInfo();
        new ManagerGetInfoOrIsbn(this, admin, ManagerGetInfoOrIsbn.DoGetUserNumber, ManagerGetInfoOrIsbn.NextChangeUserInfo);
    }

    @Override
    public void getUserInfo() {
        super.getUserInfo();
        new ManagerSearchUser(this, admin);
    }

    @Override
    public void removeUser() {
        super.removeUser();
        new ManagerGetInfoOrIsbn(this, admin, ManagerGetInfoOrIsbn.DoGetUserNumber, ManagerGetInfoOrIsbn.NextRemoveUserInfo);
    }
}
