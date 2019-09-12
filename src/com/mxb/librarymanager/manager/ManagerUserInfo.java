package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.MessageBox;
import com.mxb.librarymanager.gui.UserInfoWindow;


public class ManagerUserInfo extends UserInfoWindow {
    static final int DoChangeUserInfo = 0;
    static final int DoRemoveUserInfo = 1;
    static final int DoShowUserInfo = 2;
    static final int DoAddUserInfo = 3;
    static final String cmds[] = {"修改", "删除", "显示", "添加"};

    UserInfo admin;
    UserInfo user;
    ManagerDataProcess dataProcess = new ManagerDataProcess();
    int doWhat;

    public ManagerUserInfo(BaseWindow father, UserInfo admin, int doWhat) {
        this(father, admin, null, doWhat);
    }

    public ManagerUserInfo(BaseWindow father, UserInfo admin, UserInfo user, int doWhat) {
        super(father, cmds[doWhat]);

        if (doWhat == DoChangeUserInfo || doWhat == DoShowUserInfo || doWhat == DoRemoveUserInfo)
            setUserInfo(user);
        if (doWhat == DoChangeUserInfo)
            setNumberDisabled();
        if (doWhat == DoRemoveUserInfo || doWhat == DoShowUserInfo)
            setAllFieldDisabled();
        if (doWhat == DoChangeUserInfo || doWhat == DoAddUserInfo)
            setLendInfoDisVisible();
        if (doWhat != DoShowUserInfo)
            setManageButtonDisVisible();

        this.doWhat = doWhat;
        this.admin = admin;
        this.user = user;
    }

    @Override
    public void onOk() {
        if (doWhat == DoShowUserInfo) {
            destroy();
            return;
        }

        UserInfo user = getUserInfo();
        if (user.number.equals("") || user.password.equals("") || user.name.equals("") || user.college.equals("")) {
            new MessageBox.errorBox(this, "信息不完整");
        } else {
            String res = "";
            if (doWhat == DoChangeUserInfo)
                res = dataProcess.changeUserInfoByAdmin(admin, user);
            else if (doWhat == DoAddUserInfo)
                res = dataProcess.addUser(admin, user);
            else if (doWhat == DoRemoveUserInfo) {
                if (user.name.equals(admin.name)) {
                    new MessageBox.errorBox(this, "用户删除失败：你不能删除你自己");
                    return;
                }
                res = dataProcess.removeUser(admin, user);
            }
            if (res.equals("ok")) {
                new MessageBox.messageBox(this, "用户" + cmds[doWhat] + "成功") {
                    @Override
                    public void destroy() {
                        super.destroy();
                        father.destroy();
                    }
                };
            } else
                new MessageBox.errorBox(this, "用户" + cmds[doWhat] + "失败：" + res);
        }
    }

    @Override
    public void details() {
        super.details();
        new MessageBox.errorBox(this, "对不起，该功能尚未实现");
    }

    @Override
    public void onLendButton() {
        super.onLendButton();
    }

    @Override
    public void onReturnButton() {
        super.onReturnButton();
        new MessageBox.errorBox(this, "对不起，该功能尚未实现");
    }

    @Override
    public void onChangeButton() {
        super.onChangeButton();
        new ManagerUserInfo(this, admin, user, ManagerUserInfo.DoChangeUserInfo);
    }

    @Override
    public void onRemoveButton() {
        super.onRemoveButton();
        new ManagerUserInfo(this, admin, user, ManagerUserInfo.DoRemoveUserInfo);
    }

    @Override
    public void refresh() {
        super.refresh();
        UserInfo user = getUserInfo();
        String res = dataProcess.getUserInfo(admin, user, user.number);
        if (res.equals("ok")) {
            setUserInfo(user);
        } else {
            new MessageBox.errorBox(this, "用户刷新失败：" + res) {
                @Override
                public void destroy() {
                    super.destroy();
                    father.destroy();
                }
            };
        }
    }
}
