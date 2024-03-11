package org.groupt.kirjaarkisto.repositories;
import java.util.Optional;

import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.Kuvitus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KuvitusRepository extends JpaRepository<Kuvitus, Long> {
 Optional<Kuvitus> findByKirjaAndKuva_Id(Kirja kirja, Long kuvaId);
    void deleteByKirjaAndKuva_Id(Kirja kirja, Long kuvaId);
}