package com.project.todoappbackend;

import com.project.todoappbackend.config.RateLimitingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class ToDoAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoAppBackendApplication.class, args);

        System.out.println("Hello World!");
        System.out.println("The Program has started");
    }

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilterFilter( RateLimitingFilter rateLimitingFilter) {
        FilterRegistrationBean<RateLimitingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(rateLimitingFilter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);  // 1 order means this filters run first before any other filters
        return registration;
    }

}
