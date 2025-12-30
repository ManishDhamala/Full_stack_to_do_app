package com.project.todoappbackend;

import com.project.todoappbackend.config.RateLimitingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ToDoAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoAppBackendApplication.class, args);

        System.out.println("Hello World!");
        System.out.println("The Program has started");

    }

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilterFilter() {
        FilterRegistrationBean<RateLimitingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RateLimitingFilter());
        registration.addUrlPatterns("/api/*");
        return registration;
    }

}
