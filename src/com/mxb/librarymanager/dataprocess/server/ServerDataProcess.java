package com.mxb.librarymanager.dataprocess.server;

import com.mxb.librarymanager.dataprocess.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.awt.print.Book;
import java.util.ArrayList;

public class ServerDataProcess implements DataProcess {

    private static final String host = "http://39.108.3.243";
    //private static final String host = "http://127.0.0.1:8080";

    private static final String url = host + "/bookmanager/";
    private static final String staticUrl = host + "/bookmanager/";

    private static final String loginUrl = url + "login";
    private static final String userInfoUrl = url + "user_info";
    private static final String addUserUrl = url + "add_user";
    private static final String changePasswordUrl = url + "change_password";
    private static final String getUserInfoUrl = url + "get_user_info";
    private static final String changeUserInfoUrl = url + "change_user_info";
    private static final String removeUserUrl = url + "remove_user";
    private static final String searchUserUrl = url + "search_user";
    private static final String getCategoryUrl = url + "get_category";
    private static final String addCategoryUrl = url + "add_category";
    private static final String changeCategoryUrl = url + "change_category";
    private static final String removeCategoryUrl = url + "remove_category";
    private static final String addBookUrl = url + "add_book";
    private static final String getBookUrl = url + "get_book";
    private static final String changeBookUrl = url + "change_book";
    private static final String removeBookUrl = url + "remove_book";
    private static final String searchBookUrl = url + "search_book";


    private String checkConnect(HttpConnect httpConnect) {
        if (httpConnect.serverError)
            return "连接服务器失败：" + httpConnect.serverErrorString;
        if (httpConnect.responseCode != 200)
            return "服务器错误：" + httpConnect.responseCode + " " + httpConnect.responseCodeString;

        switch (httpConnect.code) {
        case HttpConnect.OK:
            return "ok";
        case HttpConnect.BadRequest:
            return "<内部错误>错误的POST请求参数";
        case HttpConnect.PasswordError:
            return "密码错误";
        case HttpConnect.UserTokenError:
            return "用户未登录";
        case HttpConnect.UserNotFoundError:
            return "该用户不存在";
        case HttpConnect.PermissionError:
            return "你无权进行此操作";
        case HttpConnect.UserAlreadyExistsError:
            return "用户已存在";
        case HttpConnect.CategoryAlreadyExistsError:
            return  "该图书类别已存在";
        case HttpConnect.CategoryNotFoundError:
            return "不存在该图书类别";
        case HttpConnect.BookAlreadyExistsError:
            return "该图书已存在";
        case HttpConnect.BookNotFoundError:
            return "该图书不存在";
        default:
            return "未知错误";
        }
    }

    private void getUserInfoByRes(HttpConnect httpConnect, UserInfo user, boolean getPassword) {
        user.name = httpConnect.data.getString("name");
        user.number =  httpConnect.data.getString("number");
        user.isAdmin = httpConnect.data.getInt("is_admin") == 1;
        user.canLend = httpConnect.data.getInt("can_lend") == 1;
        user.college = httpConnect.data.getString("college");
        if (getPassword)
            user.password = httpConnect.data.getString("password");
    }

    private void addUserInfo(HttpConnect httpConnect, UserInfo user) {
        httpConnect.addData("number", user.number);
        httpConnect.addData("password", user.password);
        httpConnect.addData("name", user.name);
        httpConnect.addData("is_admin", user.isAdmin ? "1" : "0");
        httpConnect.addData("college", user.college);
        httpConnect.addData("can_lend", user.canLend ? "1" : "0");
    }

    private void addBookInfo(HttpConnect httpConnect, BookInfo book) {
        httpConnect.addData("isbn", book.isbn);
        httpConnect.addData("name", book.name);
        httpConnect.addData("author", book.author);
        httpConnect.addData("press", book.press);
        httpConnect.addData("price", book.price);
        httpConnect.addData("count", book.storageCount + "");
        httpConnect.addData("des", book.des);
        httpConnect.addData("page", book.page + "");
        httpConnect.addData("category", book.category);
        if (!book.imageUrl.equals(""))
            httpConnect.addFile("image", book.imageUrl);
    }

   /* 学生和管理员共有的操作 */
        
    public String login(UserInfo user) {
        HttpConnect httpConnect = new HttpConnect(loginUrl);
        httpConnect.addData("number", user.number);
        httpConnect.addData("password", user.password);
        httpConnect.addData("is_admin", user.isAdmin ? "1" : "0");

        httpConnect.connect();
        String res = checkConnect(httpConnect);
        if (res.equals("ok")) {
            user.userToken = httpConnect.data.getString("user_token");
        }
        return res;
    }

    public String logout(UserInfo user) {
        return null;
    }

    public String getUserInfo(UserInfo user) {
        HttpConnect httpConnect = new HttpConnect(userInfoUrl);
        httpConnect.addData("user_token", user.userToken);
        httpConnect.connect();
        String res = checkConnect(httpConnect);
        if (res.equals("ok"))
            getUserInfoByRes(httpConnect, user, false);
        return res;
    }

    public String searchBook(UserInfo user, String category, int searchBy, String words, ArrayList<BookInfo> books) {
        String by[] = {"isbn", "name", "author"};

        if (words.equals(""))
            words = "*";

        HttpConnect httpConnect = new HttpConnect(searchBookUrl);
        httpConnect.addData("user_token", user.userToken);
        httpConnect.addData("category", category);
        httpConnect.addData("search_by", by[searchBy]);
        httpConnect.addData("words", words);
        httpConnect.connect();
        String res = checkConnect(httpConnect);
        if (res.equals("ok")) {
            books.clear();
            int length = httpConnect.data.getInt("length");
            JSONArray array = httpConnect.data.getJSONArray("books");
            for (int i = 0; i < length; i++) {
                JSONObject jsonBook = array.getJSONObject(i);
                BookInfo book = new BookInfo();

                book.isbn = jsonBook.getString("isbn");
                book.name = jsonBook.getString("name");
                book.category = jsonBook.getString("category");
                book.press = jsonBook.getString("press");
                book.price = jsonBook.getString("price");
                book.storageCount = jsonBook.getInt("count");
                book.lendCount = jsonBook.getInt("lend_count");
                book.imageUrl = staticUrl + jsonBook.getString("image");
                book.des = jsonBook.getString("des");
                book.author = jsonBook.getString("author");
                book.page = jsonBook.getInt("page");

                books.add(book);
            }
        }

        return res;
    }


    public String getBook(UserInfo user, BookInfo book, String isbn) {
        HttpConnect httpConnect = new HttpConnect(getBookUrl);
        httpConnect.addData("user_token", user.userToken);
        httpConnect.addData("isbn", isbn);
        httpConnect.connect();

        String res = checkConnect(httpConnect);
        if (res.equals("ok")) {
            book.isbn = httpConnect.data.getString("isbn");
            book.name = httpConnect.data.getString("name");
            book.category = httpConnect.data.getString("category");
            book.press = httpConnect.data.getString("press");
            book.price = httpConnect.data.getString("price");
            book.storageCount = httpConnect.data.getInt("count");
            book.lendCount = httpConnect.data.getInt("lend_count");
            book.imageUrl = staticUrl + httpConnect.data.getString("image");
            book.des = httpConnect.data.getString("des");
            book.author = httpConnect.data.getString("author");
            book.page = httpConnect.data.getInt("page");
        }
        return res;
    }

    public String getCategoryAllInfo(UserInfo user, ArrayList<CategoryInfo> categoryInfos) {
        HttpConnect httpConnect = new HttpConnect(getCategoryUrl);
        httpConnect.addData("user_token", user.userToken);
        httpConnect.connect();

        String res = checkConnect(httpConnect);
        if (res.equals("ok")) {
            categoryInfos.clear();
            int length = httpConnect.data.getInt("length");
            JSONArray array = httpConnect.data.getJSONArray("categories");

            for (int i = 0; i < length; i++) {
                JSONObject jsonUser = array.getJSONObject(i);
                CategoryInfo category = new CategoryInfo();
                category.name = jsonUser.getString("name");
                category.bookCount = jsonUser.getInt("book_count");
                categoryInfos.add(category);
            }
        }
        return res;
    }

    public String getLendInfoList(UserInfo user, ArrayList<LendInfo> lendInfos) {
        return null;
    }

    public String lendBook(UserInfo user, BookInfo book) {
        return null;
    }

    public String returnBook(UserInfo user, BookInfo book) {
        return null;
    }

    public String changePassword(UserInfo user, String newPassword) {
        HttpConnect httpConnect = new HttpConnect(changePasswordUrl);
        httpConnect.addData("user_token", user.userToken);
        httpConnect.addData("old_password", user.password);
        httpConnect.addData("new_password", newPassword);
        httpConnect.connect();
        return checkConnect(httpConnect);
    }

    /********** 管理员操作 *********************/

    public String getUserInfo(UserInfo admin, UserInfo user, String number) {
        HttpConnect httpConnect = new HttpConnect(getUserInfoUrl);
        httpConnect.addData("user_token", admin.userToken);
        httpConnect.addData("number", number);
        httpConnect.connect();
        String res = checkConnect(httpConnect);
        if (res.equals("ok"))
            getUserInfoByRes(httpConnect, user, true);
        return res;
    }

    public String addUser(UserInfo admin, UserInfo user) {
        HttpConnect httpConnect = new HttpConnect(addUserUrl);
        addUserInfo(httpConnect, user);
        httpConnect.addData("user_token", admin.userToken);
        httpConnect.connect();
        return checkConnect(httpConnect);
    }

    public String removeUser(UserInfo admin, UserInfo user) {
        HttpConnect httpConnect = new HttpConnect(removeUserUrl);
        httpConnect.addData("user_token", admin.userToken);
        httpConnect.addData("number", user.number);
        httpConnect.connect();
        return checkConnect(httpConnect);
    }

    public String changeUserInfoByAdmin(UserInfo admin, UserInfo user) {
        HttpConnect httpConnect = new HttpConnect(changeUserInfoUrl);
        addUserInfo(httpConnect, user);
        httpConnect.addData("user_token", admin.userToken);
        httpConnect.connect();
        return checkConnect(httpConnect);
    }

    public String searchUser(UserInfo admin, int type, String words, ArrayList<UserInfo> users) {
        final String  types[] = {"number", "name", "college", "admin", "student", "can_lend", "all"};

        HttpConnect httpConnect = new HttpConnect(searchUserUrl);
        if (words.equals(""))
            words = "*";
        httpConnect.addData("words", words);
        httpConnect.addData("user_token", admin.userToken);
        httpConnect.addData("type", types[type]);
        httpConnect.connect();
        String res = checkConnect(httpConnect);
        if (res.equals("ok")) {
            users.clear();
            int length = httpConnect.data.getInt("length");
            JSONArray array = httpConnect.data.getJSONArray("users");
            for (int i = 0; i < length; i++) {
                JSONObject jsonUser = array.getJSONObject(i);
                UserInfo user = new UserInfo();
                user.name = jsonUser.getString("name");
                user.number = jsonUser.getString("number");
                //user.password = jsonUser.getString("password");
                user.college = jsonUser.getString("college");
                user.isAdmin = jsonUser.getInt("is_admin") == 1;
                user.canLend = jsonUser.getInt("can_lend") == 1;

                users.add(user);
            }
        }
        return res;
    }

    public String getUserLendInfoList(UserInfo admin, UserInfo user, ArrayList<LendInfo> lendInfos) {
        return "";
    }

    public String getAllLendInfoList(UserInfo admin, ArrayList<LendInfo> lendInfos) {
        return null;
    }

    public String addBookInfo(UserInfo admin, BookInfo book) {
        HttpConnect httpConnect = new HttpConnect(addBookUrl);
        httpConnect.addData("user_token", admin.userToken);
        addBookInfo(httpConnect, book);
        httpConnect.connect();
        return checkConnect(httpConnect);
    }

    public String removeBookInfo(UserInfo admin, BookInfo book) {
        HttpConnect httpConnect = new HttpConnect(removeBookUrl);
        httpConnect.addData("user_token", admin.userToken);
        httpConnect.addData("isbn", book.isbn);
        httpConnect.connect();
        return checkConnect(httpConnect);
    }

    public String changeBookInfo(UserInfo admin, BookInfo book) {
        HttpConnect httpConnect = new HttpConnect(changeBookUrl);
        httpConnect.addData("user_token", admin.userToken);
        addBookInfo(httpConnect, book);
        httpConnect.connect();
        return checkConnect(httpConnect);
    }

    public String addCategoryInfo(UserInfo admin, String name) {
        HttpConnect httpConnect = new HttpConnect(addCategoryUrl);
        httpConnect.addData("user_token", admin.userToken);
        httpConnect.addData("name", name);
        httpConnect.connect();
        return checkConnect(httpConnect);
    }

    public String removeCategoryInfo(UserInfo admin, String name) {
        HttpConnect httpConnect = new HttpConnect(removeCategoryUrl);
        httpConnect.addData("user_token", admin.userToken);
        httpConnect.addData("name", name);
        httpConnect.connect();
        return checkConnect(httpConnect);
    }

    public String changeCategoryInfo(UserInfo admin, String oldName, String newName) {
        HttpConnect httpConnect = new HttpConnect(changeCategoryUrl);
        httpConnect.addData("user_token", admin.userToken);
        httpConnect.addData("name1", oldName);
        httpConnect.addData("name2", newName);
        httpConnect.connect();
        return checkConnect(httpConnect);
    }
}
