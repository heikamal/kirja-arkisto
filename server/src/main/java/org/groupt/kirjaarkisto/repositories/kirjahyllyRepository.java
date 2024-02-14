package org.groupt.kirjaarkisto.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.groupt.kirjaarkisto.models.kirjahylly;

public interface kirjahyllyRepository extends JpaRepository<kirjahylly, Long> {
    kirjahylly findByOmistaja(String omistaja);
}