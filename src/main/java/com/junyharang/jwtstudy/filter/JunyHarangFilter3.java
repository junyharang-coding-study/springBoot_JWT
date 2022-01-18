package com.junyharang.jwtstudy.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JunyHarangFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 토큰을 만들었다고 가정 했을 때, 토큰이 오면 인증이 되게 하고,
        // 그게 아니면 Filter를 못타게 해서 Controller를 못타게 해야 한다.

        // Toekn = junyharang이라고 가정
        // Token은 ID / PW가 정상적으로 인증되면 Token을 만들어주고, Token으로 응답 해 주어야 한다.
        // 클라이언트가 요청할 때마다 header Authorization에 값으로 Token을 가져 오면 해당 Token이 내가 만든 Token이 맞는지 유효성 검사를 하면 된다.
       if (req.getMethod().equals("POST")) {   // 요청의 HTTP Method가 POST라면?
            System.out.println("요청으로 POST Method가 들어왔습니다!");
            // 요청 Header에 Autorization을 받아오려 하는 부분
            String headerAuth = req.getHeader("Authorization");

            System.out.println("요청 Header의 Authorization 값 : " + headerAuth);

            System.out.println("Filter1이 동작 중 입니다!");

            if (headerAuth.equals("junyharang")) {  // 요청 Header Autorization에 "하이"가 있다면?
                // 체인을 타 계속 실행되게 한다.
                chain.doFilter(req, res);
            } else { // 요청 Header Autorization에 "하이"가 없다면?
                // chain 관련 코드가 없어 아래 내용이 실행되고, 필터가 종료된다.
                PrintWriter out = res.getWriter();
                out.println("인증이 되지 않았습니다!");
            } // if-else 문 끝
        } // if문 끝
    } // doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 끝
} // class 끝
