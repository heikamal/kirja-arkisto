package org.groupt.kirjaarkisto.config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.groupt.kirjaarkisto.models.ERole;
import org.groupt.kirjaarkisto.models.Kayttaja;
import org.groupt.kirjaarkisto.models.Role;
import org.groupt.kirjaarkisto.repositories.KayttajaRepository;
import org.groupt.kirjaarkisto.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StartUpHouseKeeper {

  @Value("${kirjaarkisto.adminPassword}")
  private String adminPassString;

  @Autowired
  private KayttajaRepository kayttajaRepository;

  @Autowired
  private RoleRepository roleRepository;
  
  @Autowired
  private PasswordEncoder encoder;

  static final String ROLE_NOT_FOUND = "Error: Role is not found.";

  @EventListener(ContextRefreshedEvent.class)
  public void onApplicationEvent(ContextRefreshedEvent event) {

    Optional<Role> testRole = roleRepository.findById(1);
    Optional<Kayttaja> testUser = kayttajaRepository.findByNimi("admin");

    if (testRole.isPresent() && testUser.isPresent()) {
      return;
    }
    
    if (!testRole.isPresent()){
      roleRepository.save(new Role(ERole.ROLE_USER));
      roleRepository.save(new Role(ERole.ROLE_ADMIN));
    }

    if (!testUser.isPresent()) {

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
}
