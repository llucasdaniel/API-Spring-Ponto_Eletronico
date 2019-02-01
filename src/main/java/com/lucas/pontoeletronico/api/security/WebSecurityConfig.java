//package com.lucas.pontoeletronico.api.security;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//@Configuration
//@ConditionalOnProperty(value = "security.basic.enabled", havingValue = "false")
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // For example: Use only Http Basic and not form login.
//        http
//            .authorizeRequests()   
//            .antMatchers( "/api/cadastrar-pj")
//            .permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .httpBasic();
//    }
//}