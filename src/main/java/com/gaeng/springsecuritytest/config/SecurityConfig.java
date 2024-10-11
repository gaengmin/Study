package com.gaeng.springsecuritytest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity //Spring Security를 활성화하는 어노테이션
@Configuration //Spring 설정 클래스임을 나타냄,
public class SecurityConfig {

    @Bean //암호화 메소드 구현
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()

                );

        http
                .formLogin((auth)->auth.loginPage("/login")
                        .loginProcessingUrl("loginProc") //login 요청시, 시큐리티가 확인하는 곳
                        .permitAll()
                );


        http
                .csrf((auth)->auth.disable());
        return http.build();
    }

}
