package com.observationclass.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RestController
@RequestMapping("/api")
public class Welcome {
    @GetMapping(value="/hello")
    public String hello(){
        return "hello world!";

    }



}
