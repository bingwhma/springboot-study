package com.linyi.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication  
@EnableDiscoveryClient  
@RestController
public class ConsulApplication {
	
	@Autowired  
    private LoadBalancerClient loadBalancer;  
      
    @Autowired  
    private DiscoveryClient discoveryClient;  
      
    /** 
     * 从所有服务中选择一个服务（轮询） 
     */  
    @RequestMapping("/discover")  
    public Object discover() {  
        return loadBalancer.choose("consul001").getUri().toString();  
    }  
      
    /** 
     * 获取所有服务  
     */  
    @RequestMapping("/services")  
    public Object services() {  
        return discoveryClient.getInstances("consul001");  
    }  
    
	@RequestMapping("/index")
	public String home() {
		return "hi ,i'm OK";
	}

	@LoadBalanced
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return getRestTemplate().getForEntity("http://consul001/hi", String.class).getBody();
    }
	public static void main(String[] args) {
		new SpringApplicationBuilder(ConsulApplication.class).web(true).run(args);
	}
}
