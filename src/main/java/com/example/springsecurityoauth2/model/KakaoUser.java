package com.example.springsecurityoauth2.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class KakaoUser extends OAuth2ProviderUser {

  public KakaoUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
    super(oAuth2User.getAttributes(), oAuth2User, clientRegistration);
  }

  @Override
  public String getId() {
    return (String) getAttributes().get("account_email");
  }

  @Override
  public String getUsername() {
    return (String) getAttributes().get("name");
  }
}
