package org.groupt.kirjaarkisto.repositories;

import java.util.Optional;

import org.groupt.kirjaarkisto.models.ERole;
import org.groupt.kirjaarkisto.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findById(Integer id);
  Optional<Role> findByName(ERole name);
  Boolean existsByName(ERole name);
}
