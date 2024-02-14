package org.groupt.kirjaarkisto.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import java.util.List;
import org.groupt.kirjaarkisto.models.kirja;

public interface kirjakopioRepository extends JpaRepository<KirjaKopio, Long> {
    List<KirjaKopio> findByKirja(kirja kirja);
}
