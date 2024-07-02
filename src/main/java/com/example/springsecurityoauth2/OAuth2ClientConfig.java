package com.example.springsecurityoauth2;

import com.example.springsecurityoauth2.service.CustomOAuth2UserService;
import com.example.springsecurityoauth2.service.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class OAuth2ClientConfig {

  private final CustomOAuth2UserService customOAuth2UserService;
  private final CustomOidcUserService customOidcUserService;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests((requests) -> requests
        .antMatchers("/static/js/**", "/static/images/**", "/static/css/**", "/static/scss/**").permitAll()
        .antMatchers("/").permitAll()
        .antMatchers("/api/user").access("hasAnyRole('SCOPE_profile','SCOPE_email')")
        .antMatchers("/api/oidc").access("hasRole('SCOPE_openid')")
        .anyRequest().authenticated());

    http.oauth2Login(oauth2 -> oauth2
        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
            .userService(customOAuth2UserService)
            .oidcUserService(customOidcUserService)
        )
    );
    http.logout().logoutSuccessUrl("/");

    return http.build();
  }
}
