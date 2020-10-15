package com.linyi.apilimit.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linyi.apilimit.limit.RateLimit;
import com.linyi.apilimit.limit.SemaphoreLimit;

@RestController
public class MyController {

	@RateLimit
    @RequestMapping("/ratelimit")
    public String ratelimit() throws Exception{
        //假设业务处理了1秒
        TimeUnit.SECONDS.sleep(1);
        return "success";
    }

    /**
     * 设置limitKey=SemaphoreKey,并且许可证只有3个
     */
    @SemaphoreLimit(limitKey ="SemaphoreKey", value =3)
    @RequestMapping("/SemaphoreLimit")
    public String SemaphoreLimit() throws Exception{
        //假设业务处理了1秒
        TimeUnit.SECONDS.sleep(1);
        return "success";
    }
    
}
