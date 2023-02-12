package com.mewcom.backend.rest.web.util;

import com.mewcom.backend.config.properties.JwtProperties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

  @Autowired
  private JwtProperties jwtProperties;

  public ResponseCookie generateJwtCookie(UserDetails userDetails) {
    String token = generateTokenFromUsername(userDetails.getUsername());
    return ResponseCookie.from(jwtProperties.getJwtCookieName(), token)
        .maxAge(24 * 60 * 60)
        .build();
  }

  private String generateTokenFromUsername(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(new Date().getTime() + jwtProperties.getJwtExpirationTimeInMillis()))
        .signWith(SignatureAlgorithm.HS512, jwtProperties.getJwtSecret())
        .compact();
  }

  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtProperties.getJwtSecret())
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public Boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtProperties.getJwtSecret()).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public ResponseCookie getCleanJwtCookie() {
    return ResponseCookie.from(jwtProperties.getJwtCookieName(), null)
        .path("/api")
        .build();
  }
}
