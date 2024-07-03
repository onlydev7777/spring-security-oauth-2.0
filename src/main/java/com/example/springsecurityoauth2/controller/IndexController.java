package com.example.springsecurityoauth2.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

  private static final String NAME_KEY = "name";

  @GetMapping("/")
  public String index(Model model, Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User) {
    if (authentication != null && authentication instanceof OAuth2AuthenticationToken) {
      OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
      OAuth2User principal = oAuth2AuthenticationToken.getPrincipal();
      Map<String, Object> attributes = oAuth2User.getAttributes();
      System.out.println("oAuth2AuthenticationToken = " + oAuth2AuthenticationToken);
      System.out.println("oAuth2AuthenticationToken.getPrincipal().equals(oAuth2User) : " + principal.equals(oAuth2User));

      //naver일 경우 response Map으로 한번 더 감싸져 있음.
      if (((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId().equals("naver")) {
        attributes = (Map<String, Object>) attributes.get("response");
      }

      String nameKey = NAME_KEY;
      //kakao일 경우 NAME_KEY : nickname
      if (((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId().equals("kakao")) {
        nameKey = "nick" + nameKey;
      }

      String name = (String) attributes.get(nameKey);
      model.addAttribute("user", name);
    }

    return "index";
  }
}
