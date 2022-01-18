package com.junyharang.jwtstudy.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JunyHarangFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("Filter1이 동작 중 입니다!");
        chain.doFilter(request, response);
    } // doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 끝
} // class 끝
