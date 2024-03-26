package org.groupt.kirjaarkisto.repositories;
import java.util.List;

import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.models.Valokuva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValokuvaRepository extends JpaRepository<Valokuva, Long> {
  List<Valokuva> findByKirjaKopio(KirjaKopio kirjaKopio);

}
