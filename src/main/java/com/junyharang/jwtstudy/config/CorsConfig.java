package com.junyharang.jwtstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration public class CorsConfig {

    @Bean public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        // BackEnd(API) Server가 응답할 때, json을 JavaScript에서 처리할 수 있게 할지에 대한 설정
        // false로 되어있다면 JavaScript로 요청을 했을 때, 응답이 오지 않게 된다.
        config.setAllowCredentials(true);
        // 모든 IP에 응답을 허용하겠다.
        config.addAllowedOrigin("*");
        // 모든 Header에 응답을 허용하겠다.
        config.addAllowedHeader("*");
        // 모든 post, get, put, delete, patch 요청을 허용 하겠다.
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    } // corsFilter() 끝

} // class 끝
