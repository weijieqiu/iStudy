package com.company;

/**
 * 抽象的 servlet 类，提供 servlet 常见的三种方法
 */
public abstract class IServlet {
    public void service(IRequest iRequest, IResponse iResponse){
        if(iRequest.getMethod().equalsIgnoreCase("POST")){
            doPost(iRequest,iResponse);
        }else if(iRequest.getMethod().equalsIgnoreCase("GET")){
            doGet(iRequest, iResponse);
        }
    }

    public void doGet(IRequest iRequest, IResponse iResponse) {

    }

    public void doPost(IRequest iRequest, IResponse iResponse) {

    }
}
