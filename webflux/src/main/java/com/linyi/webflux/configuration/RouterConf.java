package com.linyi.webflux.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.linyi.webflux.handler.CityHandler;

@Configuration
public class RouterConf {

	 @Bean
	    public RouterFunction<ServerResponse> routeCity(CityHandler cityHandler) {
	        return RouterFunctions
	                .route(RequestPredicates.GET("/city")
	                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
	                        cityHandler::helloCity);
	    }
}
