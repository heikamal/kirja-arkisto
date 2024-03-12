package org.groupt.kirjaarkisto.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.groupt.kirjaarkisto.exceptions.UserNotFoundException;
import org.groupt.kirjaarkisto.models.ERole;
import org.groupt.kirjaarkisto.models.Kayttaja;
import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.models.Role;
import org.groupt.kirjaarkisto.payload.JwtResponse;
import org.groupt.kirjaarkisto.payload.LoginRequest;
import org.groupt.kirjaarkisto.payload.MessageResponse;
import org.groupt.kirjaarkisto.payload.SignupRequest;
import org.groupt.kirjaarkisto.repositories.KayttajaRepository;
import org.groupt.kirjaarkisto.repositories.RoleRepository;
import org.groupt.kirjaarkisto.security.jwt.JwtUtils;
import org.groupt.kirjaarkisto.security.services.UserDetailsImpl;
import org.groupt.kirjaarkisto.services.KirjaHyllyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  static final String ROLE_NOT_FOUND = "Error: Role is not found.";
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

  @Autowired
  KirjaHyllyService kirjahyllyService;

  /**
   * Määrittää endpointin käyttäjän sisäänkirjautumiselle. Vastaus sisältää käyttäjän ID:n, nimen, roolit ja tokenin, 
   * mitä voi käyttää tunnistautumiseen.
   * 
   * Tulevan JSON-datan tulisi näyttää seuraavalta:
   * {
   *   "nimi": "",
   *   "salasana": "",
   * }
   * 
   * @param loginRequest LoginRequest-olio, joka sisältää kirjautuvan käyttäjän käyttäjänimen ja salasanan.
   * @return ResponseEntity-olio, joka sisää käyttäjän ID:n, nimen, roolit ja tokenin.
   */
  @PostMapping("/signin")
  public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getNimi(), loginRequest.getSalasana()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         roles);
  }

  /**
   * Määrittää endpointin käyttäjän luomiselle. Luo uuden käyttäjän nimen, salasanan ja roolien pohjalta. 
   * Vastaus sisältää tiedon siitä että käyttäjän luonti on onnistunut.
   * 
   * Tulevan JSON-datan tulisi käyttää seuraavalta:
   * {
   *   "nimi": "",
   *   "salasana": "",
   *   "rooli": ["admin", "user"]
   * }
   * 
   * @param signUpRequest SignupRequest-olio, joka sisää nimen, salasanan ja roolit.
   * @return ResponseEntity-olio, joka kertoo onnistuneesta käyttäjän luomisesta.
   */
  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

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
          .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        if ("admin".equals(role)) {
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
          roles.add(adminRole);
        } else {
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    Kayttaja saved = kayttajaRepository.save(user);

    KirjaHylly hylly = new KirjaHylly();
    hylly.setOmistaja(saved.getId());

    kirjahyllyService.saveKirjaHylly(hylly);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  /**
   * Endpointti käyttäjän hakemiselle. Hakee pyynnön tokenin omistavan käyttäjän ja vastaa sillä.
   * 
   * @return Vastaus, joka sisältää käyttäjän.
   */
  @GetMapping("/user")
  public Map<String, Object> returnUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    
    response.put("id", userDetails.getId());
    response.put("nimi", userDetails.getUsername());
    response.put("rooli", roles);
    
    return response;
  }

  @PutMapping("/user/{id}")
  public Map<String, Object> editUser(@PathVariable(value = "id") Long id, @Valid @RequestBody SignupRequest editRequest) {
    Map<String, Object> response = new HashMap<>();

    Kayttaja toBeModified = kayttajaRepository.findById(id).orElse(null);
    if (toBeModified == null) {
      throw new UserNotFoundException("Käyttäjää ei löytynyt!");
    }
    Set<String> strRoles = editRequest.getRooli();
    Set<ERole> roles = new HashSet<>();

    // Onhan tää nyt vähän spagettia mutta toimii
    if (editRequest.getNimi() != null) {
      toBeModified.setNimi(editRequest.getNimi());
    }

    if (editRequest.getSalasana() != null) {
      toBeModified.setSalasana(encoder.encode(editRequest.getSalasana()));
    }

    if (strRoles != null) {
      strRoles.forEach(role -> {
        if ("admin".equals(role)) {
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
          roles.add(adminRole.getName());
        } else {
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
          roles.add(userRole.getName());
        }
      });
    } else {
      toBeModified.getRoles().forEach(role -> roles.add(role.getName()));
    }

    kayttajaRepository.save(toBeModified);
    response.put("id", toBeModified.getId());
    response.put("nimi", toBeModified.getNimi());
    response.put("rooli", roles);
    return response;
  }
  
  @DeleteMapping("/user/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteUser(@PathVariable(value = "id") Long id) {
    kayttajaRepository.deleteById(id);
  }
}
