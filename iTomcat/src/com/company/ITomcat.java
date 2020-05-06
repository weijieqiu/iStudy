package com.company;

        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.HashMap;
        import java.util.Map;

/**
 * 启动 tomcat
 */
public class ITomcat {
    private Integer port = 8888;    // 定义socket连接端口
    private Map<String, String> urlServletMapping = new HashMap<>();    // 存储 url 和 对应的类

    public ITomcat(Integer port) {
        this.port = port;
    }

    public void start(){
        initServletMapping();
        try{
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                IRequest iRequest = new IRequest(inputStream);
                IResponse iResponse = new IResponse(outputStream);
                dispatch(iRequest, iResponse);
                socket.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void dispatch(IRequest iRequest, IResponse iResponse) {
        String clazz = urlServletMapping.get(iRequest.getUrl());

        try {
            Class<IServlet> iServletClass = (Class<IServlet>) Class.forName(clazz);
            IServlet iServlet = iServletClass.newInstance();
            iServlet.service(iRequest, iResponse);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initServletMapping() {
        for (ServletMapping servletMapping: ServletMappingConfig.servletMappingList){
            urlServletMapping.put(servletMapping.getUrl(), servletMapping.getClazz());
        }
    }

    public static void main(String[] args) {
        ITomcat iTomcat = new ITomcat(8888);
        iTomcat.start();
    }
}
