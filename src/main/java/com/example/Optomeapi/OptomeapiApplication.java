package com.example.Optomeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class OptomeapiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OptomeapiApplication.class, args);
    }
    
    @GetMapping("/api/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("database", "PostgreSQL");
        response.put("message", "Eye Care API is running!");
        return response;
    }
}