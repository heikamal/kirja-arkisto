package org.groupt.kirjaarkisto.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.groupt.kirjaarkisto.models.ERole;
import org.groupt.kirjaarkisto.models.Kayttaja;
import org.groupt.kirjaarkisto.models.Role;
import org.groupt.kirjaarkisto.payload.JwtResponse;
import org.groupt.kirjaarkisto.payload.LoginRequest;
import org.groupt.kirjaarkisto.payload.MessageResponse;
import org.groupt.kirjaarkisto.payload.SignupRequest;
import org.groupt.kirjaarkisto.repositories.KayttajaRepository;
import org.groupt.kirjaarkisto.repositories.RoleRepository;
import org.groupt.kirjaarkisto.security.jwt.JwtUtils;
import org.groupt.kirjaarkisto.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Luokka määrittämään käyttäjänhallinan ja autentikoinnin endpointit. Endpointit löytyvät osoitteesta "/api/auth/*" ja 
 * sisältävät käyttäjän luonnin ja kirjautumisen.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  /**
   * Olio, joka tarjoaa autentikoinnin metodit.
   */
  @Autowired
  AuthenticationManager authenticationManager;

  /**
   * Repository, joka hallitsee käyttäjiä tietokannassa.
   */
  @Autowired
  KayttajaRepository kayttajaRepository;

  /**
   * Repository hallitsemaan rooleja tietokannassa.
   */
  @Autowired
  RoleRepository roleRepository;

  /**
   * Salasanan enkoodaaja.
   */
  @Autowired
  PasswordEncoder encoder;

  /**
   * Tokeninhallinnan metodit.
   */
  @Autowired
  JwtUtils jwtUtils;

  /**
   * Määrittää endpointin käyttäjän sisäänkirjautumiselle. Vastaus sisältää käyttäjän ID:n, nimen, roolit ja tokenin, 
   * mitä voi käyttää tunnistautumiseen.
   * 
   * @param loginRequest LoginRequest-olio, joka sisältää kirjautuvan käyttäjän käyttäjänimen ja salasanan.
   * @return ResponseEntity-olio, joka sisää käyttäjän ID:n, nimen, roolit ja tokenin.
   */
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getNimi(), loginRequest.getSalasana()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         roles));
  }

  /**
   * Määrittää endpointin käyttäjän luomiselle. Luo uuden käyttäjän nimen, salasanan ja roolien pohjalta. 
   * Vastaus sisältää tiedon siitä että käyttäjän luonti on onnistunut.
   * 
   * @param signUpRequest SignupRequest-olio, joka sisää nimen, salasanan ja roolit.
   * @return ResponseEntity-olio, joka kertoo onnistuneesta käyttäjän luomisesta.
   */
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

    if (kayttajaRepository.existsByNimi(signUpRequest.getNimi())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    Kayttaja user = new Kayttaja(signUpRequest.getNimi(),
               encoder.encode(signUpRequest.getSalasana()));

    Set<String> strRoles = signUpRequest.getRooli();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    kayttajaRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
