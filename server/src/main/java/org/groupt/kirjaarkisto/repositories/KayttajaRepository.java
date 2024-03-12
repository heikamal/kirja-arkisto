package org.groupt.kirjaarkisto.repositories;

import java.util.Optional;

import org.groupt.kirjaarkisto.models.Kayttaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KayttajaRepository extends JpaRepository<Kayttaja, Long> {
  Optional<Kayttaja> findByNimi(String nimi);
  Boolean existsByNimi(String nimi);
}
