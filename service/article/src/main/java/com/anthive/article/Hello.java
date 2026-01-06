package com.anthive.article;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping("/")
    public String index()
    {
        return "Hello Spring Boot!";
    }
}