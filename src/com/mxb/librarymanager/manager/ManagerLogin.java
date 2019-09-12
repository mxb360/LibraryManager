package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.LoginWindow;
import com.mxb.librarymanager.gui.MessageBox;


/**
 * 登录界面的逻辑实现
 * 界面继承至界面LoginWindow，如果想更换界面，请重新实现LoginWindow（需要继承至JFrame）
 * 重新实现LoginWindow时需要提供login方法，在用户点击登录时调用该方法
 * 默认的LoginWindow，继承至BaseWindow
 */
public class ManagerLogin extends LoginWindow {
    private ManagerDataProcess managerDataProcess = new ManagerDataProcess();
    private LocalData localData = new LocalData();
    private UserInfo user = null;

    public ManagerLogin() {
        this(false);
    }

    public ManagerLogin(boolean noAutoLogin) {
        super(null);
        setAdmin(localData.isAdmin);
        setAutoLogin(localData.autoLogin);
        setRememberPassword(localData.rememberPassword);

        // 记住密码
        if (localData.rememberPassword)
            setNumberAndPassword(localData.number, localData.password);
        else
            setNumberAndPassword(localData.number, "");

        // 自动登录
        if (!noAutoLogin && localData.autoLogin) {
            setEnabled(false);
            if (localData.number.equals("") || localData.password.equals("")) {
                new MessageBox.errorBox(this, "登录失败：账号或者密码为空");
                log("登录失败：账号或者密码为空");
            }
            String loginString = login(localData.number, localData.password, localData.isAdmin);
            if (!loginString.equals("ok")) {
                new MessageBox.errorBox(this, "登录失败：" + loginString);
                log("登录失败：" + loginString);
            } else {
                log("登录成功");
                destroy();
            }
        }
    }

    @Override
    public String login(String number, String password, boolean isAdmin) {
        setLoginEnabled(false);

        user = new UserInfo(number, password, isAdmin);

        localData.number = number;
        localData.password = password;
        localData.isAdmin = isAdmin;
        localData.saveFile();

        String res = managerDataProcess.login(user);
        if (res.equals("ok")) {
            new ManagerManage(this, user);
        }

        setLoginEnabled(true);
        return res;
    }

    @Override
    public void autoLoginChecked(boolean auto) {
        super.autoLoginChecked(auto);
        localData.autoLogin = auto;
        localData.saveFile();
    }

    @Override
    public void rememberPasswordChecked(boolean remember) {
        super.rememberPasswordChecked(remember);
        localData.rememberPassword = remember;
        localData.saveFile();
    }
}
