package com.mxb.librarymanager.manager;

import java.io.*;

public class LocalData {
    private static final File file = new File("save.dat");
    private static final String magicString0 = "7sdJcK";
    private static final String magicString1 = "yrAo2H";
    private static final String regex = " ";

    private static final byte filePassword = (byte)0xf2;
    private int length = 7;

    public String number;
    public String password;
    public boolean isAdmin;
    public boolean rememberPassword;
    public boolean autoLogin;

    public LocalData() {
        readFile();
        parseFile();
    }


    String readFile() {
        String res = "";

        if (!file.exists()) {
            System.out.println("数据文件不存在");
            return "";
        }

        System.out.println("解析文件：" + file.getPath());
        try {
            InputStream in = new FileInputStream(file);
            byte[] bytes = new byte[2048];

            int n = in.read(bytes);
            // 解密
            for (int i = 0; i < n; i++)
                bytes[i] ^= filePassword;

            res += new String(bytes, 0, n);

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("数据文件内容：" + res);
        return res;
    }

    void parseFile() {
        String[] info = readFile().split(regex);
        if (info.length >= length && info[0].equals(magicString0) && info[1].equals(magicString1)) {
            number = info[2];
            password = info[3];
            isAdmin = info[4].equals("true");
            rememberPassword = info[5].equals("true");
            autoLogin = info[6].equals("true");
        } else {
            System.out.println("数据文件格式错误，使用默认数据{length: " + info.length + '}');
            number = "";
            password = "";
            isAdmin = false;
            rememberPassword =  false;
            autoLogin = false;
        }
    }

    void saveFile() {
        String res = magicString0 + regex + magicString1 + regex;
        res += number + regex;
        res += password + regex;
        res += (isAdmin ? "true" : "false") + regex;
        res += (rememberPassword ? "true" : "false") + regex;
        res += (autoLogin ? "true" : "false") + regex;
        byte[] bytes = res.getBytes();

        // 简单的加密
        for (int i = 0; i < bytes.length; i++)
            bytes[i] ^= filePassword;

        try {
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            out.write(bytes);
            out.close();
            System.out.println("数据保存成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("数据保存失败");
        }
    }
}
