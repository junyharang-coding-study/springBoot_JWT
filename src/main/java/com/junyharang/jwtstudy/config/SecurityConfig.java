package com.junyharang.jwtstudy.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // JWT 사용을 위해 Session 방식 비활성화(Stateless 서버로 만들겠다는 의미)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()  // fromLogin 방식을 사용하지 않는다.
                .httpBasic().disable()  // 기본적인 Login 방식을 사용하지 않는다.

                // /api/v1/member/ 하위 모든 곳에는 USER, MANAGER,ADMIN이 접근 가능하다.
                .authorizeRequests().antMatchers("/api/v1/member/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

                // /api/v1/manager/ 하위 모든 곳에는 MANAGER,ADMIN이 접근 가능하다.
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

                // /api/v1/admin/ 하위 모든 곳에는 ADMIN이 접근 가능하다.
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")

                // 위를 제외한 모든 곳은 권한 없이 접근 가능
                .anyRequest().permitAll();
    } // configure(HttpSecurity http) 끝
} // class 끝
