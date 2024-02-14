package org.groupt.kirjaarkisto.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.groupt.kirjaarkisto.models.kirjasarja;

public interface kirjasarjaRepository extends JpaRepository<kirjasarja, Long> {
    //täällä yapataan mitä repot tekee... siis perii kaikki jparepository metodit, siis findbyID, findAll jne...
}