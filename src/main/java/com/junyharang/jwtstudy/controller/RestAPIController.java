package com.junyharang.jwtstudy.controller;

import com.junyharang.jwtstudy.model.Member;
import com.junyharang.jwtstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// https://github.com/codingspecialist/Springboot-Security-JWT-Easy

@RequiredArgsConstructor
@RestController public class RestAPIController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("home") public String home() {
        return "<h1>home</h1>";
    } // home() 끝

    @PostMapping("token") public String token() {
        return "<h1>token</h1>";
    } // token() 끝

    @PostMapping("join") public String join(@RequestBody Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRolse("ROLE_USER");
        memberRepository.save(member);
        return "회원가입완료";
    } // join(@RequestBody Member member) 끝

} // class 끝
