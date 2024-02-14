package org.groupt.kirjaarkisto.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.groupt.kirjaarkisto.models.kirjasarja;
import java.util.List;
import org.groupt.kirjaarkisto.models.kirja;


public interface kirjaRepository extends JpaRepository<kirja, Long> {
    List<kirja> findByKirjasarja(kirjasarja kirjasarja);
}