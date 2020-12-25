/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: HttpUtil
 * Author:   Administrator
 * Date:     2020/11/10 15:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wangpeng.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/11/10
 * @since 1.0.0
 */
public class HttpUtil {


    public static String httpPost(String url, String jsonPar, String token) {
        String returnValue = "这是默认返回值，接口调用失败";

        //第一步：创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);

            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(jsonPar,"utf-8");
//            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            // 添加请求头token
            httpPost.setHeader("Token",token);
            // 伪造http请求ip地址
            httpPost.addHeader("x-forwarded-for","119.123.209.243");
            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;
    }


    public static String httpGet(String url, String token) {
        String returnValue = "这是默认返回值，接口调用失败";

        //第一步：创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{

            //第二步：创建httpPost对象
            HttpGet httpGet = new HttpGet(url);

            //第三步：给httpPost设置JSON格式的参数
//            StringEntity requestEntity = new StringEntity(jsonPar,"utf-8");
//            requestEntity.setContentEncoding("UTF-8");
            httpGet.setHeader("Content-type", "application/json");
            // 添加请求头token
            httpGet.setHeader("Token",token);
            // 伪造http请求ip地址
            httpGet.addHeader("x-forwarded-for","119.123.209.243");
//            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpGet,responseHandler); //调接口获取返回值时，必须用此方法

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;
    }

    public static String httpRequestData(String url, JSONObject obj) throws IOException {
        // TODO Auto-generated method stub
        URL u;
        HttpURLConnection con;
        DataOutputStream osw;
        StringBuffer buffer = new StringBuffer();
        u = new URL(url);
        con = (HttpURLConnection) u.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setUseCaches(false);
        con.setInstanceFollowRedirects(true);
        con.setRequestProperty("Content-Type", "application/json");
        osw = new DataOutputStream(con.getOutputStream());
        osw.writeBytes(obj.toString());
        osw.flush();
        osw.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String temp;
        while ((temp = br.readLine()) != null) {
            buffer.append(temp);
            buffer.append('\n');
        }
        return buffer.toString();
    }

}