package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.ListWindow;
import com.mxb.librarymanager.gui.MessageBox;

import java.util.ArrayList;

public class ManagerShowUserSearchInfo extends ListWindow {
    UserInfo admin;
    Object[][] info;
    ManagerDataProcess dataProcess = new ManagerDataProcess();

    public ManagerShowUserSearchInfo(BaseWindow father, UserInfo admin, Object[][] info, String[] head) {
        super(father, "", info, head);
        this.admin = admin;
        this.info = info;
    }

    @Override
    public void rowChicked(int index) {
        UserInfo user = new UserInfo();
        String res = dataProcess.getUserInfo(admin, user, (String)info[index][0]);
        if (res.equals("ok")) {
            new ManagerUserInfo(this, admin, user, ManagerUserInfo.DoShowUserInfo);
        } else
            new MessageBox.errorBox(this, "获取用户信息失败：" + res);
    }
}
