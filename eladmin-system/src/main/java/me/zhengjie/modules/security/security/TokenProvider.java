package me.zhengjie.modules.security.security;

import cn.hutool.core.util.ObjectUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.security.config.SecurityProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author /
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

   private final SecurityProperties properties;
   private static final String AUTHORITIES_KEY = "auth";
   private Key key;

   public TokenProvider(SecurityProperties properties) {
      this.properties = properties;
   }


   @Override
   public void afterPropertiesSet() {
      byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
      this.key = Keys.hmacShaKeyFor(keyBytes);
   }

   public String createToken(Authentication authentication) {
      String authorities = authentication.getAuthorities().stream()
         .map(GrantedAuthority::getAuthority)
         .collect(Collectors.joining(","));

      long now = (new Date()).getTime();
      Date validity = new Date(now + properties.getTokenValidityInSeconds());

      return Jwts.builder()
         .setSubject(authentication.getName())
         .claim(AUTHORITIES_KEY, authorities)
         .signWith(key, SignatureAlgorithm.HS512)
         .setExpiration(validity)
         .compact();
   }

   Authentication getAuthentication(String token) {
      Claims claims = Jwts.parser()
         .setSigningKey(key)
         .parseClaimsJws(token)
         .getBody();

      // fix bug: 当前用户如果没有任何权限时，在输入用户名后，刷新验证码会抛IllegalArgumentException
       Object authoritiesStr = claims.get(AUTHORITIES_KEY);
      Collection<? extends GrantedAuthority> authorities =
              ObjectUtil.isNotEmpty(authoritiesStr) ?
       Arrays.stream(authoritiesStr.toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()) : Collections.emptyList();

      User principal = new User(claims.getSubject(), "", authorities);

      return new UsernamePasswordAuthenticationToken(principal, token, authorities);
   }

   boolean validateToken(String authToken) {
      try {
         Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
         return true;
      } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
         log.info("Invalid JWT signature.");
         e.printStackTrace();
      } catch (ExpiredJwtException e) {
         log.info("Expired JWT token.");
         e.printStackTrace();
      } catch (UnsupportedJwtException e) {
         log.info("Unsupported JWT token.");
         e.printStackTrace();
      } catch (IllegalArgumentException e) {
         log.info("JWT token compact of handler are invalid.");
         e.printStackTrace();
      }
      return false;
   }

   public String getToken(HttpServletRequest request){
      final String requestHeader = request.getHeader(properties.getHeader());
      if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
         return requestHeader.substring(7);
      }
      return null;
   }
}
