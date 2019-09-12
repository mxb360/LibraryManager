package com.mxb.librarymanager.dataprocess.database;

import com.mxb.librarymanager.dataprocess.*;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MysqlDataProcess implements DataProcess {
    private static final String HOST = "39.108.3.243";
    private static final int PORT = 3306;
    private static final String DATABASE = "library_manager";
    private static final String USER = "root";
    private static final String PASSWORD = "1324212023";

    private static final String USER_TABLE = "lms_user";

    private static DataBaseUtils dataBaseUtils;
    private boolean connectError;

    public MysqlDataProcess() {
        if (dataBaseUtils == null) {
            dataBaseUtils = new DataBaseUtils(USER, PASSWORD, DATABASE, HOST, PORT);
            connectError = !dataBaseUtils.connect();
        }
    }

    private boolean tryConnect() {
        if (connectError)
            connectError = !dataBaseUtils.connect();
        return !connectError;
    }

    private String querySql(String tableName, String conString , String conValue) {
        return "select * from " + tableName + " where " + conString + "=\"" + conValue + "\"";
    }

    private UserInfo getUserByNumber(String number) {
        UserInfo user = new UserInfo();
        try {
            ResultSet res = dataBaseUtils.query(querySql(USER_TABLE, "number", number));
            if (res.next()) {
                user.number = res.getString("number");
                user.name = res.getString("name");
                user.password = res.getString("password");
                user.isAdmin = res.getInt("is_admin") == 1;
                user.canLend = res.getInt("can_lend") == 1;
                user.college = res.getString("college");
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }

    private void log(String logString) {
        System.out.println("数据库信息：" + logString);
    }


    /********************************************************/


    @Override
    public String login(UserInfo user) {
        UserInfo userInDatabase = getUserByNumber(user.number);
        if (userInDatabase != null && user.password.equals(userInDatabase.password)) {
            user.userToken = user.number;
            return "ok";
        }

        return "账号或者密码错误";
    }

    @Override
    public String logout(UserInfo user) {
        return "ok";
    }

    @Override
    public String getUserInfo(UserInfo user) {
        UserInfo userInDatabase = getUserByNumber(user.userToken);
        if (userInDatabase != null) {
            user.number = userInDatabase.number;
            user.name = userInDatabase.name;
            user.isAdmin = userInDatabase.isAdmin;
            user.canLend = userInDatabase.canLend;
            user.college = userInDatabase.college;
            return "ok";
        }
        return "找不到相关用户";
    }

    @Override
    public String searchBook(UserInfo user, String category, int searchBy, String words, ArrayList<BookInfo> books) {
        return "";
    }

    @Override
    public String getBook(UserInfo user, BookInfo book, String isbn) {
        return "";
    }

    @Override
    public String getCategoryAllInfo(UserInfo user, ArrayList<CategoryInfo> categoryInfos) {
        return "";
    }

    @Override
    public String getLendInfoList(UserInfo user, ArrayList<LendInfo> lendInfos) {
        return "";
    }

    @Override
    public String lendBook(UserInfo user, BookInfo book) {
        return "";
    }

    @Override
    public String returnBook(UserInfo user, BookInfo book) {
        return "";
    }

    @Override
    public String changePassword(UserInfo user, String newPassword) {
        return "";
    }

    @Override
    public String getUserInfo(UserInfo admin, UserInfo user, String number) {
        user.number = number;
        return getUserInfo(user);
    }

    @Override
    public String addUser(UserInfo admin, UserInfo user) {
        return "";
    }

    @Override
    public String removeUser(UserInfo admin, UserInfo user) {
        return "";
    }

    @Override
    public String changeUserInfoByAdmin(UserInfo admin, UserInfo user) {
        return "";
    }

    @Override
    public String searchUser(UserInfo admin, int type, String words, ArrayList<UserInfo> users) {
        return "";
    }

    @Override
    public String getUserLendInfoList(UserInfo admin, UserInfo user, ArrayList<LendInfo> lendInfos) {
        return "";
    }

    @Override
    public String getAllLendInfoList(UserInfo admin, ArrayList<LendInfo> lendInfos) {
        return "";
    }

    @Override
    public String addBookInfo(UserInfo admin, BookInfo book) {
        return "";
    }

    @Override
    public String removeBookInfo(UserInfo admin, BookInfo book) {
        return "";
    }

    @Override
    public String changeBookInfo(UserInfo admin, BookInfo book) {
        return "";
    }

    @Override
    public String addCategoryInfo(UserInfo admin, String name) {
        return "";
    }

    @Override
    public String removeCategoryInfo(UserInfo admin, String name) {
        return "";
    }

    @Override
    public String changeCategoryInfo(UserInfo admin, String oldName, String newName) {
        return "";
    }
}
