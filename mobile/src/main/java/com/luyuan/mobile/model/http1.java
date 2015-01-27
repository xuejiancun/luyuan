package com.luyuan.mobile.model;

import com.luyuan.mobile.util.MyGlobal;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class http1 {
    /*
        * POST请求
        */
    public static boolean DEBUG_MODE = false;
    public static DefaultHttpClient client = new DefaultHttpClient();
    public static String serverUrl = MyGlobal.SERVER_URL_PREFIX;
    //public static String serverUrl = "https://erp.luyuan.cn";
    // public static String serverUrl = "http://192.168.10.100:8080";
    //	public static String serverUrl = "http://192.168.10.90";
//	public static String serverUrl = "http://192.168.10.60:801";
    public static String PHPSESSID = null;

    public static String PostData(List<BasicNameValuePair> list, String urlString) throws Exception {
        try {
            HttpPost post = new HttpPost(serverUrl + urlString);

            if (null != PHPSESSID) {
                // post.setHeader("Cookie", "ASP.NET_SessionId=" + PHPSESSID);
            }
            HttpEntity httpEntity = new UrlEncodedFormEntity(list, HTTP.UTF_8);// 使用编码构建Post实体
            post.setEntity(httpEntity);// 设置Post实体
            post.setHeader("Cookie", "ASP.NET_SessionId=" + MyGlobal.getUser().getSessionId());
            HttpResponse response = client.execute(post);// 执行Post方法
//            List<Cookie> cookies = client.getCookieStore().getCookies();
//            for (int i = 0; i < cookies.size(); i++) {
//                if (!DEBUG_MODE && "ASP.NET_SessionId".equals(cookies.get(i).getName())) {
//                    PHPSESSID = cookies.get(i).getValue();
//                    break;
//                } else if (DEBUG_MODE && serverUrl.contains(cookies.get(i).getDomain()) && "ASP.NET_SessionId".equals(cookies.get(i).getName())) {
//                    PHPSESSID = cookies.get(i).getValue();
//                }
//            }

            return EntityUtils.toString(response.getEntity());

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String GetData(String url) {
        HttpGet get = new HttpGet(serverUrl + url);

        if (null != PHPSESSID) {
            //get.setHeader("Cookie", "ASP.NET_SessionId=" + PHPSESSID);
        }
        //HttpClient client=new DefaultHttpClient();
        try {
            HttpResponse response = client.execute(get);//执行Post方法


            List<Cookie> cookies = client.getCookieStore().getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                if ("ASP.NET_SessionId".equals(cookies.get(i).getName())) {
                    PHPSESSID = cookies.get(i).getValue();
                    break;
                }
            }


            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            // TODO: handle exception
            return e.getMessage();
        }
    }

}
