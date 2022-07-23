package com.thf.common.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class SendSMS {
    public static Boolean sendSMS(String code, String phone,String appcode) {
        //CloseableHttpClient：建立一个可以关闭的httpClient
        //这样使得创建出来的HTTP实体，可以被Java虚拟机回收掉，不至于出现一直占用资源的情况。
        try {
            PostMethod post = new PostMethod("https://dfsns.market.alicloudapi.com/data/send_sms");
            post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            post.setRequestHeader("Authorization", "APPCODE " + appcode);

            //设置发送的数据
            NameValuePair[] data = {new NameValuePair("content", "code:" + code + ",expire_at:5"),
                    new NameValuePair("phone_number", "13592735358"),
                    new NameValuePair("template_id", "TPL_0001")};
            post.setRequestBody(data);
            HttpClient httpClient = new HttpClient();
            int statusCode = httpClient.executeMethod(post);
            //获取返回值
            if(statusCode==200){
                return true;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
