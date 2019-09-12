package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.MessageBox;
import com.mxb.librarymanager.gui.SearchWindow;

import java.util.ArrayList;

public class ManagerSearchUser extends SearchWindow {
    private static final String types[] = {"账号", "姓名", "学院", "所有管理员", "所有学生", "所有可借书用户", "所有用户"};
    private static final int disableIndex[] = {3, 4, 5, 6};

    UserInfo user;
    ManagerDataProcess dataProcess = new ManagerDataProcess();

    public ManagerSearchUser(BaseWindow father, UserInfo user) {
        super(father, "用户", types, disableIndex);
        this.user = user;
    }

    @Override
    public void search(String searchString, int index) {
        /*
        if (index == 0 || index == 1 || index == 2) {
            if (searchString.equals("")) {
                new MessageBox.errorBox(this, "信息不完整");
                return;
            }
        }
        */

        log("搜索：" + searchString + "  [" + types[index] + "]");
        ArrayList<UserInfo> users = new ArrayList<UserInfo>();
        String res = dataProcess.searchUser(this.user, index, searchString, users);
        int i = 0;
        if (res.equals("ok")) {
            Object[][] info = new Object[users.size()][];

            for (UserInfo user : users) {
                info[i] = new String[]{user.number, user.name, user.college, user.isAdmin?"是":"否", user.canLend?"是":"否"};
                log(++i + ": <name: " + user.name + " number: " + user.number + " college: " + user.college + ">");
            }
            String head[] = {"账号", "姓名", "学院", "管理员", "能借书"};
            new ManagerShowUserSearchInfo(this, user, info, head);

        } else {
            new MessageBox.errorBox(this, "搜索用户失败：" + res);
        }
    }
}
