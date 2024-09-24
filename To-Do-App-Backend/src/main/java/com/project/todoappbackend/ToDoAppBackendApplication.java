package com.project.todoappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoAppBackendApplication.class, args);

        System.out.println("Hello World!");
        System.out.println("The Program has started");

    }

}
