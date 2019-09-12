package com.mxb.librarymanager.dataprocess;

import java.util.ArrayList;

/* 处理后台数据的接口
 * 注意：对于所有的返回字符串类型的接口，如果操作成功，返回“OK”，否则返回出错信息
 */
public interface DataProcess {
    static final int SearchByNumber = 0;
    static final int SearchByName = 1;
    static final int SearchByCollege = 2;
    static final int SearchAllAdmin = 3;
    static final int SearchAllStudent = 4;
    static final int SearchAllCanLend = 5;
    static final int SearchAllUser = 6;
    
    /* 学生和管理员共有的操作 */
    
    public String login(UserInfo user);
    
    public String logout(UserInfo user);

    public String getUserInfo(UserInfo user);

    public String searchBook(UserInfo user, String category, int searchBy, String words, ArrayList<BookInfo> books);

    public String getBook(UserInfo user, BookInfo book, String isbn);

    public String getCategoryAllInfo(UserInfo user, ArrayList<CategoryInfo> categoryInfos);

    public String getLendInfoList(UserInfo user, ArrayList<LendInfo> lendInfos);

    public String lendBook(UserInfo user, BookInfo book);

    public String returnBook(UserInfo user, BookInfo book);

    public String changePassword(UserInfo user, String newPassword);

    /********** 管理员操作 *********************/

    public String getUserInfo(UserInfo admin, UserInfo user, String number);
    
    public String addUser(UserInfo admin, UserInfo user);

    public String removeUser(UserInfo admin, UserInfo user);

    public String changeUserInfoByAdmin(UserInfo admin, UserInfo user);

    public String searchUser(UserInfo admin, int type, String words, ArrayList<UserInfo> users);
    
    public String getUserLendInfoList(UserInfo admin, UserInfo user, ArrayList<LendInfo> lendInfos);
    
    public String getAllLendInfoList(UserInfo admin, ArrayList<LendInfo> lendInfos);
    
    public String addBookInfo(UserInfo admin, BookInfo book);
    
    public String removeBookInfo(UserInfo admin, BookInfo book);
    
    public String changeBookInfo(UserInfo admin, BookInfo book);
    
    public String addCategoryInfo(UserInfo admin, String name);
    
    public String removeCategoryInfo(UserInfo admin, String name);
    
    public String changeCategoryInfo(UserInfo admin, String oldName, String newName);
}
