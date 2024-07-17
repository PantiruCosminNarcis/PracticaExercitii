package com.example.Spring.Security5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.netty.DisposableServer;

@ComponentScan(basePackages = {"com.example.Spring.Security5"})
@EnableWebFlux
public class SpringSecurity6Application {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringSecurity6Application.class)) {
			DisposableServer server = context.getBean(DisposableServer.class);
			server.onDispose().block();
		}
	}
}
