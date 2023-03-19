package com.mewcom.backend.config.filter;

import com.mewcom.backend.rest.web.service.helper.AuthenticationServiceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private AuthenticationServiceHelper authenticationServiceHelper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String token = request.getHeader("Authorization");

      if (token != null && token.startsWith("Bearer ") &&
          SecurityContextHolder.getContext().getAuthentication() == null) {
        token = token.substring(7);
        authenticationServiceHelper.verifyIdTokenAndSetAuthentication(token);
      }
    } catch (Exception e) {
      log.error("Cannot set user authentication: {}", e.getMessage());
    }

    filterChain.doFilter(request, response);
  }
}
