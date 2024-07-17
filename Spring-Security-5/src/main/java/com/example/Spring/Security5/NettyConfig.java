package com.example.Spring.Security5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.http.server.HttpServer;
import reactor.netty.DisposableServer;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;

@Configuration
@ComponentScan(basePackages = {"com.example.Spring.Security5"})
public class NettyConfig {

    @Bean
    public HttpHandler httpHandler(ApplicationContext context) {
        return WebHttpHandlerBuilder.applicationContext(context).build();
    }

    @Bean
    public ReactorHttpHandlerAdapter reactorHttpHandlerAdapter(HttpHandler httpHandler) {
        return new ReactorHttpHandlerAdapter(httpHandler);
    }

    @Bean
    public HttpServer httpServer() {
        return HttpServer.create()
                .host("localhost")
                .port(8080);
    }

    @Bean
    public DisposableServer nettyContext(HttpServer httpServer, ReactorHttpHandlerAdapter adapter) {
        return httpServer.handle(adapter).bindNow();
    }
}
