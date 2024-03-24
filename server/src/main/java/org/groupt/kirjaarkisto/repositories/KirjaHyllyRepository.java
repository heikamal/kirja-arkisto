package org.groupt.kirjaarkisto.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.models.KirjaSarja;

public interface KirjaHyllyRepository extends JpaRepository<KirjaHylly, Long> {
    Optional<KirjaHylly> findByOmistaja(Long omistaja);
    List<KirjaHylly> findByOmatSarjatIn(List<KirjaSarja> omatSarjat);
}