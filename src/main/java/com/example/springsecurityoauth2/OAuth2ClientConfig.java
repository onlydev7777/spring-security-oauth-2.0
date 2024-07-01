package com.example.springsecurityoauth2;

import com.example.springsecurityoauth2.filter.CustomOAuth2LoginAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class OAuth2ClientConfig {
  private final ClientRegistrationRepository clientRegistrationRepository;
  private final DefaultOAuth2AuthorizedClientManager authorizedClientManager;
  private final OAuth2AuthorizedClientRepository authorizedClientRepository;
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .antMatchers("/login/loginPage", "/oauh2Login", "/client").permitAll()
        .anyRequest().authenticated()
        .and()
//        .oauth2Login(Customizer.withDefaults());
    .oauth2Client(Customizer.withDefaults());

    http.addFilterBefore(customOAuth2LoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//    http.logout(logout->logout
//        .logoutSuccessHandler(oidcLogoutSuccessHandler())
//        .invalidateHttpSession(true)
//        .clearAuthentication(true)
//        .deleteCookies("JSESSIONID")
//    );

//        .oauth2Login(login->login.loginPage("/loginPage"));


    return http.build();
  }

  private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
    OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
    successHandler.setPostLogoutRedirectUri("http://localhost:8081/login");
    return successHandler;
  }

  public CustomOAuth2LoginAuthenticationFilter customOAuth2LoginAuthenticationFilter() throws Exception {
    CustomOAuth2LoginAuthenticationFilter customOAuth2LoginAuthenticationFilter =
        new CustomOAuth2LoginAuthenticationFilter(authorizedClientManager, authorizedClientRepository);
    customOAuth2LoginAuthenticationFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
      response.sendRedirect("/home");
    });
    return customOAuth2LoginAuthenticationFilter;
  }
}
