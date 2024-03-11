package org.groupt.kirjaarkisto.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import java.util.List;
import org.groupt.kirjaarkisto.models.Kirja;

public interface KirjaKopioRepository extends JpaRepository<KirjaKopio, Long> {
    List<KirjaKopio> findByBook(Kirja book);
}
