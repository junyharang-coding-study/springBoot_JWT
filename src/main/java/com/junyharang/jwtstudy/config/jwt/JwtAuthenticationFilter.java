package com.junyharang.jwtstudy.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junyharang.jwtstudy.auth.PrincipalDetails;
import com.junyharang.jwtstudy.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        try {
//            BufferedReader reader = request.getReader();
//
//            String input = null;
//
//            while ((input = reader.readLine()) != null) {
//                System.out.println(input);
//            } // while 문 끝

            // JSON으로 전달된 값을 Parsing 할수 있게 해주는 객체 생성
            ObjectMapper om = new ObjectMapper();
            Member member = om.readValue(request.getInputStream(), Member.class);
            System.out.println(member);

            // Token 생성(회원의 이름과 Password를 통해)
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());

            // PrincipalDetailsService의 loadUserByUsername()이 실행된 뒤 정상이면 authentication이 반환된다.
            // authenticationManager에 Token을 넣어 호출한다.
            // authentication 변수에는 Login 정보가 담긴다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);


            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

            // 출력이 된다면 Login이 되었다는 의미
            System.out.println("로그인 완료된 계정 : " + principal.getMember().getUsername());

            // authentication 객체가 Session 영역에 저장된다.
            // 반환해 주는 이유는 권한 관리를 Spring Security가 대신 해 주기 때문에 편하게 코딩하기 위해서 반환해 준다.
            // 굳이 JWT를 사용하면서 Session을 만들 이유가 없다. 단지 권한 처리 때문에 Session에 authentication을 넣어주는 것 뿐.
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        } // try-cache 문 끝

        System.out.println("===========================================");
        // 2. authenticationManager로 정상 인지 로그인 시도하면 PrincipalDetailsService가 호출된다.
        //    해당 Class안에 loadUserByUsername()가 자동 호출

        // 3. PrincipalDetails를 Session에 담는다.
        //    Session에 해당 내용을 담는 이유는 담지 않으면 권한에 대한 처리를 할 수 없기 때문이다.

        // 4. JWT를 만들어서 응답해 준다.

        return null;
    } // attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 끝

    // attemptAuthentication() 실행 뒤 인증이 정상처리 되면 successfulAuthentication()가 실행된다.
    // successfulAuthentication()에서 JWT를 만든 뒤 요청 이용자에게 JWT를 응답 처리 해 주면 된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication()가 실행 되었습니다! 인증이 정상 처리 되었나 보네요🤭");
        super.successfulAuthentication(request, response, chain, authResult);
    } // successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) 끝
} // class 끝
