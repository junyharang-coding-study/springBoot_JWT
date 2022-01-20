package com.junyharang.jwtstudy.config.jwt;

/*
    이용자가 JWT를 보냈을 때, 해당 JWT 유효성을 검사하는 Filter

    Security가 Filter를 가지고 있는데, 그 Filter 중 BasicAuthenticationFilter가 있다.
    권한이나, 인증이 필요한 특정 주소를 이용자가 요청하면 BasicAuthenticationFilter를 무조건 타야 한다.
    만약 권한이나, 인증이 필요한 요청이 아니라면 BasicAuthenticationFilter를 타지 않는다.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.junyharang.jwtstudy.auth.PrincipalDetails;
import com.junyharang.jwtstudy.model.Member;
import com.junyharang.jwtstudy.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private MemberRepository memberRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        System.out.println("JwtAuthorizationFilter의 AuthenticationManager authenticationManager)가 동작하였습니다!");

        this.memberRepository = memberRepository;

    } // JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) 끝

    // 인증, 권한이 필요한 주소 요청이 있을 때 이 Method를 호출한다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("이용자가 인증, 권한이 필요한 요청을 하였습니다!");

        // header에 Authrization 값 확인
        String jwtHeader = request.getHeader("Authorization");
        System.out.println("JWT Header의 값은 " + jwtHeader + "입니다.");

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) { // jwtHeader가 null이거나, header에 시작 문자열이 Bearer 이 아니라면?
            // 다시 Filter Chain을 타게 한다.
            chain.doFilter(request, response);
            return;
        } // if문 끝

        // JWT 검증을 하여 정상 이용자인지 확인
        // 요청 Header Authorization에 Bearer 문구를 공백으로 치환하여 token 변수에 담는다.
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        // JunyHarang이라고 서명하는 HMAC512 알고리즘을 쓰는 jwt에 JWT 값으로 서명을 한 뒤 그 안에 있을 username에 값을 문자열로 변환하여 가져온다.
        String userName = JWT.require(Algorithm.HMAC512("JunyHarang")).build().verify(token).getClaim("username").asString();

        if (userName != null) { // userName이 Null이 아니라면?
            // 서명이 정상적으로 되었다고 판단됨.

            // 해당 이용자가 DB에 있는지 확인
            Member memberEntity = memberRepository.findByUsername(userName);

            PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);
            // JWT 서명을 통해 서명이 정상이면 Authentication 객체를 만들어준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            // 강제로 Security Session에 접근하여 Authentication 객체 저장.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } // if문 끝
        chain.doFilter(request, response);
    } // doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 끝
} // class 끝
