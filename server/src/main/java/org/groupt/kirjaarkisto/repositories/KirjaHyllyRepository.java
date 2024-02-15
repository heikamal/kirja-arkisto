package org.groupt.kirjaarkisto.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.groupt.kirjaarkisto.models.KirjaHylly;

public interface KirjaHyllyRepository extends JpaRepository<KirjaHylly, Long> {
    KirjaHylly findByOmistaja(String omistaja);
}