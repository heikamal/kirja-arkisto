package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import java.util.List;
import org.groupt.kirjaarkisto.repositories.KirjaSarjaRepository;

@Service
public class KirjaSarjaService {

    @Autowired
    private KirjaSarjaRepository kirjaSarjaRepository;

    public List<KirjaSarja> getKirjasarjat() {
        return kirjaSarjaRepository.findAll();
    }

    public KirjaSarja getKirjasarjaById(Long id) {
        return kirjaSarjaRepository.findById(id).orElse(null);
    }

    /**
     * Tallentaa parametreina annetun kirjasarjan tietokantaan.
     * 
     * @param sarja tallennettava kirjasarja
     * @return Tallennettu kirjasarja.
     */
    public KirjaSarja saveKirjaSarja(@NonNull KirjaSarja sarja) {
      try {
        return kirjaSarjaRepository.save(sarja);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid input parameters!");
      }
    }

    /**
     * Poistaa kirjasarjan tietokannasta annetun ID-numeron perusteella. Ei palauta mitään.
     * 
     * @param id Poistettavan kirjasarjan ID kokonaislukuna.
     */
    public void removeKirjaSarja(@NonNull Long id) {
        kirjaSarjaRepository.deleteById(id);
    }
}