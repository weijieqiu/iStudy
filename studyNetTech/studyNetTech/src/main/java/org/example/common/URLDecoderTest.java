package org.example.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLDecoderTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 将普通字符串转换为
        // application/x-www-form-urlencoded 字符串
        // GBK 占两个字节所以 UTF-8 占三个字节
        String urlStr = URLEncoder.encode("中文", "GBK");
        System.out.println(urlStr);
        System.out.println(URLDecoder.decode("%D6%D0%CE%C4", "GBK"));
        String urlStr1 = URLEncoder.encode("中文", "UTF-8");
        System.out.println(urlStr1);
        System.out.println(URLDecoder.decode("%E4%B8%AD%E6%96%87", "UTF-8"));

    }
}
