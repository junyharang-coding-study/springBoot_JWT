package com.junyharang.jwtstudy.auth;

import com.junyharang.jwtstudy.model.Member;
import com.junyharang.jwtstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
    http://localhost:8080/login 요청이 올 때 동작
 */

@RequiredArgsConstructor
@Service public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByUsername()이 동작하였습니다!");
        Member memberEntity = memberRepository.findByUsername(username);

        System.out.println("memberEntity : " + memberEntity);

        return new PrincipalDetails(memberEntity);
    } // loadUserByUsername(String username) 끝
} // class 끝
