package org.groupt.kirjaarkisto.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import java.util.List;
import org.groupt.kirjaarkisto.models.Kirja;


public interface KirjaRepository extends JpaRepository<Kirja, Long> {
    List<Kirja> findByKirjaSarja(KirjaSarja kirjaSarja);
}