package com.junyharang.jwtstudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController public class RestAPIController {

    @GetMapping("home") public String home() {
        return "<h1>home</h1>";
    } // home() 끝

} // class 끝