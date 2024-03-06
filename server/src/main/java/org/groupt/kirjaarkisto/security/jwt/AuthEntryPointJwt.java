package org.groupt.kirjaarkisto.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Luokka implementoimaan AuthenticationEntryPoint-rajapinta.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  /**
   * Loggeri jotta voidaan tulostaa ilmoituksia konsoliin.
   */
  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  /**
   * Määrittää virheen jossa käyttäjää ei olla voitu tunnistaa.
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    logger.error("Unauthorized error: {}", authException.getMessage());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
  }
}
