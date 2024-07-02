package com.example.springsecurityoauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/api/user")
  public Authentication user(Authentication authorization, @AuthenticationPrincipal OAuth2User oAuth2User) {
    System.out.println("authorization = " + authorization + ", oAuth2User = " + oAuth2User);
    return authorization;
  }

  @GetMapping("/api/oidc")
  public Authentication oidc(Authentication authorization, @AuthenticationPrincipal OidcUser oidcUser) {
    System.out.println("authorization = " + authorization + ", oidcUser = " + oidcUser);
    return authorization;
  }
}
