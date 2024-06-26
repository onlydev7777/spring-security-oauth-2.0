package com.example.springsecurityoauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class OAuth2ClientConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
//        .antMatchers("/loginPage").permitAll()
        .anyRequest().authenticated()
        .and()
        .oauth2Login(Customizer.withDefaults());
//        .oauth2Login(login->login.loginPage("/loginPage"));
    return http.build();
  }

}
