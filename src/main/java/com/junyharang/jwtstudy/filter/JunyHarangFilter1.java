package com.junyharang.jwtstudy.filter;

import javax.servlet.*;
import java.io.IOException;

public class JunyHarangFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filter1이 동작 중 입니다!");
        // chain으로 넘겨주는 내용인데, 이렇게 해야 프로그램이 끝나지 않고, 계속 동작한다.
        chain.doFilter(request, response);
    } // doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 끝
} // class 끝
