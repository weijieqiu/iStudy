package com.company;

import java.io.IOException;

public class HelloServlet extends IServlet {

    @Override
    public void doGet(IRequest iRequest, IResponse iResponse) {
        try {
            iResponse.write("get hello servlet");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(IRequest iRequest, IResponse iResponse) {
        try{
            iResponse.write("post hello servlet");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
