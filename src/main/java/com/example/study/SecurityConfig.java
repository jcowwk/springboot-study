package com.example.study;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final OAuth2MemberService oAuth2MemberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .httpBasic((httpBaisc)->
                        httpBaisc.disable())
                .csrf((csrf)->
                        csrf.disable())
                .authorizeRequests()
                .requestMatchers("/private/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .oauth2Login((oauth2Login)->
                        oauth2Login
                                .loginPage("/loginForm")
                                .defaultSuccessUrl("/")
                                .userInfoEndpoint((userInfoEndpoint) ->
                                        userInfoEndpoint
                                                .userService(oAuth2MemberService)))
                .build();
    }
}