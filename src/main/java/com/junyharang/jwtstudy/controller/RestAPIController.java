package com.junyharang.jwtstudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController public class RestAPIController {

    @GetMapping("home") public String home() {
        return "<h1>home</h1>";
    } // home() 끝

    @PostMapping("token") public String token() {
        return "<h1>token</h1>";
    } // token() 끝

} // class 끝
