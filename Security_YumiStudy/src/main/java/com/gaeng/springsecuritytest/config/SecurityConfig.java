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
                        .requestMatchers("/", "/login", "/loginProc", "/join", "joinProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );
        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc") //login 요청시, 시큐리티가 확인하는 곳
                        .permitAll()
                );
//        CSRF 방지
//        http
//                .csrf((auth) -> auth.disable());

        //중복 로그인 설정 ||
        // maxSessionPreventsLogin(불린) : 다중 로그인 개수를 초과하였을 경우 처리 방법
        //true : 초과시 새로운 로그인 차단
        //false : 초과시 기존 세션 하나 삭제

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));

//
//        //세션 고정 보호
//        http
//                .sessionManagement((auth) -> auth
//                        .sessionFixation().changeSessionId());

        //로그아웃
        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }

}
