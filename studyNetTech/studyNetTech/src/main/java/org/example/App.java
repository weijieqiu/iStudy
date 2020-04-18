package org.example;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws UnknownHostException {
        InetAddress ip =  InetAddress.getByName("www.baidu.com");
        System.out.println( "Hello World!" );
    }
}
