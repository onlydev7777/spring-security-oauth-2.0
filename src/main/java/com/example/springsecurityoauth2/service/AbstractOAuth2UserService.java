package com.example.springsecurityoauth2.service;

import com.example.springsecurityoauth2.model.GoogleUser;
import com.example.springsecurityoauth2.model.KakaoUser;
import com.example.springsecurityoauth2.model.KeycloakUser;
import com.example.springsecurityoauth2.model.NaverUser;
import com.example.springsecurityoauth2.model.ProviderUser;
import com.example.springsecurityoauth2.model.User;
import com.example.springsecurityoauth2.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Getter
@Service
public class AbstractOAuth2UserService {

  private final UserRepository userRepository;
  private final UserService userService;

  protected void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {
    User user = userRepository.findByUsername(providerUser.getUsername());
    if (user == null) {
      userService.register(userRequest.getClientRegistration().getRegistrationId(), providerUser);
    }
  }

  protected ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
    String registrationId = clientRegistration.getRegistrationId();
    if ("keycloak".equals(registrationId)) {
      return new KeycloakUser(oAuth2User, clientRegistration);
    } else if ("google".equals(registrationId)) {
      return new GoogleUser(oAuth2User, clientRegistration);
    } else if ("naver".equals(registrationId)) {
      return new NaverUser(oAuth2User, clientRegistration);
    } else if ("kakao".equals(registrationId)) {
      return new KakaoUser(oAuth2User, clientRegistration);
    }
    return null;
  }
}
