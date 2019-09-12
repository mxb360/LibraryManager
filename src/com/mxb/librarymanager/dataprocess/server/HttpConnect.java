package com.mxb.librarymanager.dataprocess.server;

import java.io.*;

import net.sf.json.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpConnect {
    static final int OK = 0;
    static final int BadRequest = 1;
    static final int UserNotFoundError = 2;
    static final int UserTokenError = 3;
    static final int PasswordError = 4;
    static final int PermissionError = 5;
    static final int UserAlreadyExistsError = 6;
    static final int CategoryAlreadyExistsError = 7;
    static final int CategoryNotFoundError = 8;
    static final int BookAlreadyExistsError = 9;
    static final int BookNotFoundError = 10;
    

    private String result = "";
    int responseCode = 0; 
    boolean serverError = false;
    String responseCodeString = "";
    String serverErrorString = "";
    int code = -1;
    String message = "";
    JSONObject data = null;

    private CloseableHttpClient httpclient;
    private HttpPost httppost;
    private MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    private JSONObject json = null;

    
    public HttpConnect(String url) {
        httpclient = HttpClients.createDefault();
        httppost = new HttpPost(url);
    }
    
    public void addData(String name, String value) {
        builder.addPart(name, new StringBody(value, ContentType.create("multipart/form-data", "utf-8")));
    }

    public void addFile(String name, String fileName) {
        builder.addPart(name, new FileBody(new File(fileName)));
    }
    
    public boolean connect() {
        post();
        if (!serverError && responseCode == 200) {
            return true;
        }
        return false;
    }
    
    public void post() {
        log("+------------------------------------------------------");
        log("| url: " + httppost.getURI());

        try {
            httppost.setEntity(builder.build());

            CloseableHttpResponse response = httpclient.execute(httppost);
            responseCode = response.getStatusLine().getStatusCode();
            responseCodeString = response.getStatusLine().getReasonPhrase();

            HttpEntity entity = response.getEntity();
            if (entity != null && responseCode == 200) {
                result = EntityUtils.toString(entity);
                json = JSONObject.fromObject(result);
                if (json != null) {
                    code = json.getInt("code");
                    data = json.getJSONObject("data");
                    message = json.getString("message");
                }
            }

            response.close();
        } catch (FileNotFoundException e ) {
            serverError = true;
            serverErrorString = "指定的图片文件不存在";

        } catch (IOException e) {
            serverError = true;
            serverErrorString = "";
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log("| serverError: " + serverError);
        log("| responseCode: " + responseCode + " " + responseCodeString);
        log("| code: " + code + " " + message);
        log("| data: " + (data == null ? data : data.toString()));
        log("+------------------------------------------------------");
    }
    
    public static void log(String string) {
        System.out.println("Http信息：" + string);
    } 
}
