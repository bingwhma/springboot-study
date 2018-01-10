package com.linyi.service.impl;

import java.util.Date;

import com.linyi.service.DemoServer;

public class DemoServerImpl implements DemoServer {  
    public String sayHello(String str) {  
        str = "Hello " + str + "2:" + new Date();  
        System.out.println("server:" + str);  
        return str;  
    }

}
