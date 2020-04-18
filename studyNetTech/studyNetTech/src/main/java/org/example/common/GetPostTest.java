package org.example.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class GetPostTest {

    /**
     * 向指定 URL 发送 GET 方式的请求
     * @param url
     * @param param
     * @return
     */
    public static String sendGet(String url, String param){
        String result = "";
        String urlName = url + "?" + param;
        try {
            URL realUrl = new URL(urlName);
            // 打开和 URL 之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 获取所有的响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()){
                System.out.println(key + "--->" + map.get(key));
            }
            try {
                // 定义 BufferedReader 输入流来读取 URL 的响应
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = in.readLine()) != null){
                    result += "\n" + line;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 指定 URL 发送 POST 方式的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，格式应该满足 name1=value1&name2=value2 的形式
     * @return URL 代表远程资源的响应
     */
    public static String sendPost(String url, String param){
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; sv1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            try {
                // 定义 BufferedReader 输入流来读取 URL 的响应
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = in.readLine()) != null){
                    result += "\n" + line;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        // 发送 GET 请求
        // String s = GetPostTest.sendGet("http://www.baidu.com", null);
        // System.out.println(s);
        // 发送 POST 请求
        String s1 = GetPostTest.sendGet("http://www.baidu.com", null);
        System.out.println(s1);
    }
}
