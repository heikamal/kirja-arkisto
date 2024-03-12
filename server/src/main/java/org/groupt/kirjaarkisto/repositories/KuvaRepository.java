package org.groupt.kirjaarkisto.repositories;
import org.groupt.kirjaarkisto.models.Kuva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KuvaRepository extends JpaRepository<Kuva, Long> {
    // Voit lisätä tänne tarvittaessa omia metodeita
}
