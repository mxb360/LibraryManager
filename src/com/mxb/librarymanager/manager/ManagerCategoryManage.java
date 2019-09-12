package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.CategoryInfo;
import com.mxb.librarymanager.dataprocess.UserInfo;
import com.mxb.librarymanager.gui.BaseWindow;
import com.mxb.librarymanager.gui.CategoryManageWindow;
import com.mxb.librarymanager.gui.MessageBox;

import java.util.ArrayList;

public class ManagerCategoryManage extends CategoryManageWindow {
    UserInfo admin;
    ArrayList<CategoryInfo> categoryInfo = new ArrayList<CategoryInfo>();
    ManagerDataProcess dataProcess = new ManagerDataProcess();
    String[] category;

    public ManagerCategoryManage(BaseWindow father, UserInfo admin) {
        super(father);

        this.admin = admin;
        getCategory();
    }

    private void getCategory() {
        String res = dataProcess.getCategoryAllInfo(admin, categoryInfo);
        if (res.equals("ok")) {
            String[] _category = new String[categoryInfo.size()];
            category = new String[categoryInfo.size() - 1];
            _category[0] = CategoryInfo.NULL_CATEGORY_NAME;
            int flag = 1, n = 0;

            for (int i = 0; i < categoryInfo.size(); i++) {
                CategoryInfo info = categoryInfo.get(i);
                log(i + " <name: " + info.toUI() + " bookCount: " + info.bookCount + ">");

                if (info.isNullCategory()) {
                    _category[0] = info.toUI() + "  (存书" + info.bookCount + "本)";
                    flag = 0;
                } else {
                    _category[i + flag] = info.toUI() + "  (存书" + info.bookCount + "本)";
                    category[n++] = info.toUI();
                }
            }

            setCategory(_category);
        } else {
            new MessageBox.errorBox(this, "获取图书类别失败：" + res) {
                @Override
                public void destroy() {
                    super.destroy();
                    father.destroy();
                }
            };
        }
    }

    @Override
    public void onAdd() {
        super.onAdd();
        new ManagerChangeCategory(this, admin, ManagerChangeCategory.DoAddCategory);
    }

    @Override
    public void onChange(int index) {
        super.onChange(index);
        if (index <= 0)
            index = 1;
        index--;
        new ManagerChangeCategory(this, admin, category, index, ManagerChangeCategory.DoChangeCategory);
    }

    @Override
    public void onRemove(int index) {
        super.onRemove(index);
        if (index <= 0)
            index = 1;
        index--;
        new ManagerChangeCategory(this, admin, category, index, ManagerChangeCategory.DoRemoveCategory);
    }

    @Override
    public void subWindowDestroyed() {
        super.subWindowDestroyed();
        getCategory();
    }
}
