package com.example.springsecurityoauth2.service;

import com.example.springsecurityoauth2.model.ProviderUser;
import com.example.springsecurityoauth2.model.User;
import com.example.springsecurityoauth2.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Getter
@Service
public class UserService {

  private final UserRepository userRepository;

  public void register(String registrationId, ProviderUser providerUser) {
    User user = User.builder()
        .registrationId(registrationId)
        .id(providerUser.getId())
        .username(providerUser.getUsername())
        .password(providerUser.getPassword())
        .provider(providerUser.getProvider())
        .email(providerUser.getEmail())
        .authorities(providerUser.getAuthorities())
        .build();

    userRepository.register(user);
  }
}
