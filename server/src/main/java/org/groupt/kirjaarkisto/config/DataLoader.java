package org.groupt.kirjaarkisto.config;

import org.groupt.kirjaarkisto.repositories.KayttajaRepository;
import org.groupt.kirjaarkisto.repositories.RoleRepository;

import java.util.HashSet;
import java.util.Set;

import org.groupt.kirjaarkisto.models.ERole;
import org.groupt.kirjaarkisto.models.Kayttaja;
import org.groupt.kirjaarkisto.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

  @Value("${kirjaarkisto.adminPassword}")
  private String adminPassString;

  static final String ROLE_NOT_FOUND = "Error: Role is not found.";

  private KayttajaRepository kayttajaRepository;

  private RoleRepository roleRepository;
  
  private PasswordEncoder encoder;

  @Autowired
  public DataLoader(KayttajaRepository kayttajaRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
    this.encoder = encoder;
    this.kayttajaRepository = kayttajaRepository;
    this.roleRepository = roleRepository;

    init();
  }

  private void init(){
    
    if (Boolean.TRUE.equals(roleRepository.existsByName(ERole.ROLE_USER))) {
      return;
    }
    roleRepository.save(new Role(ERole.ROLE_USER));
    roleRepository.save(new Role(ERole.ROLE_ADMIN));

    if (Boolean.TRUE.equals(kayttajaRepository.existsByNimi("admin"))) {
      return;
    }

    Kayttaja admin = new Kayttaja("admin", encoder.encode(adminPassString));

    Set<Role> roles = new HashSet<>();

    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
    roles.add(userRole);

    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
          .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
    roles.add(adminRole);

    admin.setRoles(roles);

    kayttajaRepository.save(admin);
  }
}
