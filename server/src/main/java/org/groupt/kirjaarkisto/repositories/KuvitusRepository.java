package org.groupt.kirjaarkisto.repositories;
import java.util.List;
import java.util.Optional;

import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.Kuvitus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KuvitusRepository extends JpaRepository<Kuvitus, Long> {
  List<Kuvitus> findByKirja(Kirja kirja);
}