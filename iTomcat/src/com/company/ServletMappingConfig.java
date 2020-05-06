package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * 类似于 web.xml 把一个一个 servlet 标签注册进来
 */
public class ServletMappingConfig {
    public static List<ServletMapping> servletMappingList = new ArrayList<>();

    static {
        servletMappingList.add(new ServletMapping("index", "/index", "com.company.HelloServlet"));
    }
}

