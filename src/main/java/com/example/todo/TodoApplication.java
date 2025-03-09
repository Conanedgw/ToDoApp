package com.example.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//curl -X POST http://localhost:8080/tasks/add -H "Content-Type: application/json" -d "{\"taskName\": \"エージェント登録\"}"

@SpringBootApplication
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }
}

