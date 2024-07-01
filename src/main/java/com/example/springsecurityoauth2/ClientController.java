package com.example.springsecurityoauth2;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ClientController {
  private final OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
  private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
  private final String CLIENT_REGISTRATION_ID = "keyclock";

  @GetMapping("/client")
  public String client(HttpServletRequest request, Model model){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //인가 데이터 get
    OAuth2AuthorizedClient oAuth2AuthorizedClient1 = oAuth2AuthorizedClientRepository.loadAuthorizedClient(CLIENT_REGISTRATION_ID, authentication,
        request);
    OAuth2AuthorizedClient oAuth2AuthorizedClient2 = oAuth2AuthorizedClientService.loadAuthorizedClient(CLIENT_REGISTRATION_ID,
        authentication.getName());

    System.out.println("oAuth2AuthorizedClient1 = " + oAuth2AuthorizedClient1);
    System.out.println("oAuth2AuthorizedClient2 = " + oAuth2AuthorizedClient2);

    OAuth2AccessToken accessToken = oAuth2AuthorizedClient1.getAccessToken();

    //인증 처리 시작
    OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = oAuth2UserService.loadUser(new OAuth2UserRequest(oAuth2AuthorizedClient1.getClientRegistration(), accessToken));

    OAuth2AuthenticationToken authenticationToken = new OAuth2AuthenticationToken(oAuth2User, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")),
        CLIENT_REGISTRATION_ID);

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    model.addAttribute("accessToken", oAuth2AuthorizedClient1.getAccessToken().getTokenValue());
    model.addAttribute("refreshToken", oAuth2AuthorizedClient1.getRefreshToken().getTokenValue());
    model.addAttribute("principalName", oAuth2User.getName());
    model.addAttribute("clientName", oAuth2AuthorizedClient1.getClientRegistration().getClientName());

    return "client";
  }
}
