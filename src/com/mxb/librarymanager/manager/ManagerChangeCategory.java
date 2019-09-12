package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.CategoryChangeWindow;
import com.mxb.librarymanager.gui.MessageBox;

public class ManagerChangeCategory extends CategoryChangeWindow {
    public static final int DoAddCategory = 0;
    public static final int DoChangeCategory = 1;
    public static final int DoRemoveCategory = 2;

    private static final String[] cmd = {"添加", "修改", "删除"};

    private int doWhat;
    private String[] category;
    private UserInfo admin;
    private ManagerDataProcess dataProcess = new ManagerDataProcess();

    public ManagerChangeCategory(BaseWindow father, UserInfo admin, int doWhat) {
        this(father, admin, null, -1, doWhat);
    }

    public ManagerChangeCategory(BaseWindow father, UserInfo admin, String[] category, int defaultIndex, int doWhat) {
        super(father, cmd[doWhat]);
        this.doWhat = doWhat;
        this.category = category;
        this.admin = admin;

        if (doWhat == DoAddCategory) {
            setDisabledLine(0);
            setSecondLineWords("添加类型：");
        } else if (doWhat == DoChangeCategory) {
            setFirstLineWords("将");
            setSecondLineWords("修改为：");
            setCategory(category, defaultIndex);
        } else if (doWhat == DoRemoveCategory) {
            setDisabledLine(1);
            setFirstLineWords("删除：");
            setCategory(category, defaultIndex);
        }
        setNoteLabelVisible(doWhat == DoRemoveCategory);
    }

    @Override
    public void onCmd(String textString, int index) {
        super.onCmd(textString, index);
        String res = "";

        if (doWhat != DoRemoveCategory && textString.equals("")) {
            new MessageBox.errorBox(this, "信息不完整");
            return;
        }

        if (doWhat == DoAddCategory)
            res = dataProcess.addCategoryInfo(admin, textString);
        else if (doWhat == DoChangeCategory)
            res = dataProcess.changeCategoryInfo(admin, category[index], textString);
        else if (doWhat == DoRemoveCategory)
            res = dataProcess.removeCategoryInfo(admin, category[index]);
        if (res.equals("ok")) {
            new MessageBox.messageBox(this, "类别信息" + cmd[doWhat] + "成功") {
                @Override
                public void destroy() {
                    super.destroy();
                    father.destroy();
                }
            };
        } else {
            new MessageBox.errorBox(this, "类别信息" + cmd[doWhat] + "失败：" + res) {
                @Override
                public void destroy() {
                    super.destroy();
                    father.destroy();
                }
            };
        }
    }
}
