package com.junyharang.jwtstudy.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 스프링 시큐리티에 UsernamePasswordAuthenticationFilter가 있다.
// /login이 요청 오면 username, password를 전송하면 (Post로)
// UsernamePasswordAuthenticationFilter가 동작한다.
// 현재는 SecurityConfig에서 FormLogin을 disable을 시켜서 동작하지 않는다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override   // /login 요청이 들어오면 로그인 시도를 위해 실행되는 Method
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter : 로그인 시도 중 입니다!");

        // 1. username, password를 받는다.
        // 2. authenticationManager로 정상 인지 로그인 시도하면 PrincipalDetailsService가 호출된다.
        //    해당 Class안에 loadUserByUsername()가 자동 호출

        // 3. PrincipalDetails를 Session에 담는다.
        //    Session에 해당 내용을 담는 이유는 담지 않으면 권한에 대한 처리를 할 수 없기 때문이다.

        // 4. JWT를 만들어서 응답해 준다.

        return super.attemptAuthentication(request, response);
    } // attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 끝
} // class 끝
