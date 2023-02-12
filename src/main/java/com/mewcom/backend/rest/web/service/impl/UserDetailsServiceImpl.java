package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.model.dto.UserDetailsCustomDto;
import com.mewcom.backend.model.entity.Role;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.repository.RoleRepository;
import com.mewcom.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    return UserDetailsCustomDto.builder()
        .id(user.getId())
        .firstname(user.getFirstname())
        .lastname(user.getLastname())
        .username(user.getUsername())
        .email(user.getEmail())
        .password(user.getPassword())
        .authorities(getUserAuthorities(user))
        .build();
  }

  private List<SimpleGrantedAuthority> getUserAuthorities(User user) {
    if (Objects.isNull(user.getRoleId())) return null;
    Role role = roleRepository.findById(user.getRoleId()).orElse(null);
    if (Objects.isNull(role)) return null;
    return Collections.singletonList(new SimpleGrantedAuthority(role.getTitle()));
  }
}
