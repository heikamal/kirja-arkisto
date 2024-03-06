package org.groupt.kirjaarkisto.security.jwt;


import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.groupt.kirjaarkisto.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * Luokka määrittämään suodatin tuleville pyynnöille.
 */
public class AuthTokenFilter extends OncePerRequestFilter {

  /**
   * JwtUtils-olento tuomaan JSON-webtokeneiden metodit.
   */
  @Autowired
  private JwtUtils jwtUtils;

  /**
   * UserDetailsServiceImpl-olento jotta voidaan hallita tietokannassa olevia käyttäjiä.
   */
  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  /**
   * Loggeri tulostamaan konsoliin.
   */
  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  /**
   * Metodi tarkastamaan että pyynnön mukana tuleva tokeni on hyväksytty ja vielä olemassa.
   * 
   * @param request Pyyntö.
   * @param response Vastaus.
   * @param filterChain Filtterijono.
   * @throws ServletException Virhe, jos filtterijonon suorittaminen on keskeytynyt.
   * @throws IOException Filtterijonossa on tapahtunut I/O-virhe.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  /**
   * Palauttaa pyynnön mukana tulevan tokenin. Etsii pyynnöstä oikean otsikon ja palauttaa sieltä oikean sisällön.
   * 
   * @param request Pyyntö.
   * @return JSON-webtoken merkkijonona.
   */
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }

    return null;
  }
}
