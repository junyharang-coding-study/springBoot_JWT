package com.junyharang.jwtstudy.config;

import com.junyharang.jwtstudy.filter.JunyHarangFilter1;
import com.junyharang.jwtstudy.filter.JunyHarangFilter3;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // BasicAuthenticationFilter가 동작하기 전에 내가 만든 필터를 동작시켜 준다.
        http.addFilterBefore(new JunyHarangFilter3(), BasicAuthenticationFilter.class);

        http.csrf().disable();
        // JWT 사용을 위해 Session 방식 비 활성화(Stateless 서버로 만들겠다는 의미)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // 모든 요청은 해당 Filter를 거치게 함으로 Cors Error를 벗어날 수 있다.(Cross Origin 요청도 허용된다.)
            // Controller에 달아주는 @CrossOrigin은 인증이 없을 때 사용하는 것이고,
            // 인증이 있을때는 아래와 같이 Security Filter에 등록 인증을 만들어주어야 한다.
            .addFilter(corsFilter)
            // FrontEnd에서 Form Tag 만들어서 Login 처리하는 것을 하지 않겠다는 의미
            .formLogin().disable()  // fromLogin 방식을 사용하지 않는다.
            // httpBasic 방식은 Header에 Authorization 영역에 이용자 계정을 담아서 전달하는 방식이다.
            // 이 방식은 Header 값이 암호화 할 수 없는 단점이 있어 중간자 공격 등에 탈취가 될 수 있기 때문에 보안상 위험하다.
            // httpBasic을 쓰면서 위 단점을 보완하기 위해서 https를 사용하는 것이다.
            .httpBasic().disable()  // 기본적인 Login 방식을 사용하지 않는다.

            // JWT를 사용하는 것은 header Authorization 영역에 Token을 넣어서 보내는 방식이다.
            // Bearer 방식은 header에 ID, PW를 함께 전달하는 방식이 아니라, Token만 넣어서 보내는 방식이다.

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
