package com.halimah.latihanrequirement.configuration;


import com.halimah.latihanrequirement.filter.CustomAuthorizationFilter;
import com.halimah.latihanrequirement.handler.CustomAccessDeniedHandler;
import com.halimah.latihanrequirement.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] PUBLIC_URLS = {
            "/user/login/**",
            "user/verify/code/**"
    };
    private final BCryptPasswordEncoder encoder;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final CustomAuthorizationFilter customAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(PUBLIC_URLS).permitAll()
                            .requestMatchers(OPTIONS).permitAll()
                            .requestMatchers(DELETE, "/user/delete/**").hasAuthority("DELETE:USER")
                            .requestMatchers(DELETE, "/customer/delete/**").hasAuthority("DELETE:CUSTOMER")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return new ProviderManager(authProvider);
    }
}
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@EnableMethodSecurity
//public class SecurityConfig {
//    private final BCryptPasswordEncoder encoder;
//    private final CustomAccessDeniedHandler customAccessDeniedHandler;
//    private final CustomAuthenticationEntrypoint customAuthenticationEntryPoint;
//    private final UserDetailsService userDetailsService;
//    private final CustomAuthorizationFilter customAuthorizationFilter;
//
//    private static final String[] PUBLIC_URLS={
//            "/user/login/**",
//            "/user/verify/code/**"
//    };
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(request -> {
//                    request.requestMatchers(PUBLIC_URLS).permitAll()
//                            .requestMatchers(OPTIONS).permitAll()
//                            .requestMatchers(DELETE,"/user/delete/**").hasAuthority("DELETE:USER")
//                            .requestMatchers(DELETE,"/customer/delete/**").hasAuthority("DELETE:CUSTOMER")
//                            .anyRequest().authenticated();
//                })
//                .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement(session-> session.sessionCreationPolicy(STATELESS))
//                .exceptionHandling(exception-> exception.accessDeniedHandler(customAccessDeniedHandler)
//                        .authenticationEntryPoint(customAuthenticationEntryPoint));
//        return http.build();
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(){
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(encoder);
//        return new ProviderManager(authProvider);
//    }
//}
//
//
//
