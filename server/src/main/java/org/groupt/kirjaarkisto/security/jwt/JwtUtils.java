package org.groupt.kirjaarkisto.security.jwt;

import java.security.Key;
import java.util.Date;

import org.groupt.kirjaarkisto.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Luokka määrittämään metodit JSON-webtokeneiden tekemistä varten. Näitä tokeneita käytetään autentikoimaan eri pyynnöt.
 */
@Component
public class JwtUtils {

  /**
   * Loggeri tulostamaan viestejä konsoliin.
   */
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  /**
   * JSON-webtokeneiden tekemiseen käytetty salausavain. Muista määrittää tietoturvallisella tavalla.
   */
  @Value("${kirjaarkisto.app.jwtSecret}")
  private String jwtSecret;

  /**
   * Tokenin aikaraja millisekunteina.
   */
  @Value("${kirjaarkisto.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  /**
   * Luo uuden JSON-webtokenin annetun tunnistepyynnön pohjalta.
   * 
   * @param authentication Tunnistepyyntö, joka sisältää valtuutetun käyttäjän.
   * @return Uusi JSON-webtoken merkkijonona.
   */
  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }
  
  /**
   * Luo uuden avaimen salausavaimen pohjalta.
   *
   * @return Uusi avain <a href="https://javadoc.io/doc/io.jsonwebtoken/jjwt-api/latest/index.html">Keys</a>-oliona.
   */
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  /**
   * Palauttaa sen käyttäjän nimen, jolle annettu token kuuluu.
   * 
   * @param token JSON-webtoken merkkijonona
   * @return Käyttäjän nimi merkkijonona.
   */
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
               .parseClaimsJws(token).getBody().getSubject();
  }

  /**
   * Tarkastaa onko tokeni validi ja vielä olemassa. Mahdollisia ongelmia ovat vain väärä tokeni, tokeni on vanhentunut, 
   * sitä ei tueta tai annettu tokeni on syystä tai toisesta tyhjä.
   * 
   * @param authToken Autentikaatiotokeni merkkijonona.
   * @return Totuusarvomuuttuja, joka on tosi jos tokeni käy ja epätosi jos se ei ole voimassa.
   */
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
