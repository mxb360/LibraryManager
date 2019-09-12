package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.ChangePasswordWindow;
import com.mxb.librarymanager.gui.MessageBox;

public class ManagerChangePassword extends ChangePasswordWindow {
    private UserInfo user;
    private ManagerDataProcess dataProcess = new ManagerDataProcess();

    public ManagerChangePassword(BaseWindow father, UserInfo user) {
        super(father);
        this.user = user;
    }

    @Override
    public void onOk() {
        String oldPassword = getOldPassword();
        String newPassword = getNewPassword();
        String confirmPassword = getConfirmPassword();
        String passwordBak = user.password;

        if (!newPassword.equals(confirmPassword)) {
            new MessageBox.errorBox(this, "新密码和确认密码不一致");
        } else if (oldPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")) {
            new MessageBox.errorBox(this, "信息不完整");
        }else {
            user.password = oldPassword;
            String res = dataProcess.changePassword(user, newPassword);
            if (res.equals("ok")) {
                user.password = newPassword;
                new MessageBox.messageBox(this, "密码修改成功");
                super.onOk();
            } else {
                user.password = passwordBak;
                new MessageBox.errorBox(this, "密码修改失败：" + res);
            }
        }
    }
}
