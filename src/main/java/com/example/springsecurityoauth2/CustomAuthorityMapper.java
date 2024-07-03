package com.example.springsecurityoauth2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

public class CustomAuthorityMapper implements GrantedAuthoritiesMapper {

  private static final String PREFIX = "ROLE_";

  @Override
  public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
    Set<GrantedAuthority> result = new HashSet<>(authorities.size());
    for (GrantedAuthority authority : authorities) {
      System.out.println("authority = " + authority);
      result.add(mapAuthority(authority.getAuthority()));
    }
    return result;
  }

  /**
   * google > authorityName : http://~~~~~.{authorityName} 형태로 출력
   *
   * @param authorityName
   * @return
   */
  private GrantedAuthority mapAuthority(String authorityName) {
    System.out.println("authorityName = " + authorityName);
    int lastIndexOf = authorityName.lastIndexOf(".");
    if (lastIndexOf > 0) {
      authorityName = "SCOPE_" + authorityName.substring(lastIndexOf + 1);
    }
    if (!authorityName.startsWith(PREFIX)) {
      authorityName = PREFIX + authorityName;
    }
    return new SimpleGrantedAuthority(authorityName);
  }
}
