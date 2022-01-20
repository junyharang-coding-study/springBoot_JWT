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
        return "회원 가입 완료";
    } // join(@RequestBody Member member) 끝

    @GetMapping("/api/v1/member") public String member() {  // user 이상 권한 접근 가능
        return "member";
    } // member() 끝

    @GetMapping("/api/v1/manager") public String manager() {  // manager, admin 권한만 접근 가능
        return "manager";
    } // manager() 끝

    @GetMapping("/api/v1/admin") public String admin() {    // admin 권한만 접근 가능
        return "admin";
    } // admin() 끝

} // class 끝
