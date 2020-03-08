package ru.olegraskin.gpzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class GpZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpZuulApplication.class, args);
	}

}
