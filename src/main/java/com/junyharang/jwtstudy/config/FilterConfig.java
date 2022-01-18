package com.junyharang.jwtstudy.config;

import com.junyharang.jwtstudy.filter.JunyHarangFilter1;
import com.junyharang.jwtstudy.filter.JunyHarangFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration public class FilterConfig {

    @Bean public FilterRegistrationBean<JunyHarangFilter1> filter1() {
        FilterRegistrationBean<JunyHarangFilter1> bean = new FilterRegistrationBean<>(new JunyHarangFilter1());

        bean.addUrlPatterns("/*");
        // 낮은 번호가 필터중에서 가장 먼저 실행
        bean.setOrder(0);

        return bean;
    } // filter1() 끝

    @Bean public FilterRegistrationBean<JunyHarangFilter2> filter2() {
        FilterRegistrationBean<JunyHarangFilter2> bean = new FilterRegistrationBean<>(new JunyHarangFilter2());

        bean.addUrlPatterns("/*");
        // 낮은 번호가 필터중에서 가장 먼저 실행
        bean.setOrder(1);

        return bean;
    } // filter1() 끝
} // class 끝
