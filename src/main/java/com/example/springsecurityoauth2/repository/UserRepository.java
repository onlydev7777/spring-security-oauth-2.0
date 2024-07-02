package com.example.springsecurityoauth2.repository;

import com.example.springsecurityoauth2.model.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  private Map<String, User> users = new HashMap<>();

  public User findByUsername(String username) {
    if (users.containsKey(username)) {
      return users.get(username);
    }
    return null;
  }

  public void register(User user) {
    if (users.containsKey(user.getUsername())) {
      return;
    }

    users.put(user.getUsername(), user);
  }
}
