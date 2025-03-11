package com.jesus.voice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { httpBasic -> httpBasic.disable() }
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.disable() }
            .authorizeHttpRequests { httpBasic ->
                httpBasic
                    .requestMatchers(
                        AntPathRequestMatcher("/"),
                        AntPathRequestMatcher("/css/**"),
                        AntPathRequestMatcher("/favicon/**"),
                        AntPathRequestMatcher("/favicon.ico"),
                        AntPathRequestMatcher("/images/**"),
                        AntPathRequestMatcher("/js/**"),
                        AntPathRequestMatcher("/error"),

                        AntPathRequestMatcher("/management/**"),
                        AntPathRequestMatcher("/sermon/**")
                    ).permitAll()
            }
        return http.build()
    }
}
