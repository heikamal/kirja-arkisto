package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaSarjaException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaSarjaException;
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

    /**
     * Palauttaa kirjasarjan, joka haetaan tietokannasta annetun ID-numeron perusteella. Jos kirjasarjaa ei ole, heittää virheen. Ei tule palauttamaan tyhjää kirjasarjaa.
     *
     * @param id Haetun kirjasarjan ID kokonaislukuna.
     * @return Haettu kirjasarja.
     * @throws NonExistingKirjaSarjaException Jos kirjasarjaa ei ole
     */
    public KirjaSarja getKirjasarjaById(Long id) {
      KirjaSarja sarja = kirjaSarjaRepository.findById(id).orElse(null);
      if (sarja != null) {
        return sarja;
      } else {
        throw new NonExistingKirjaSarjaException("Kirjasarja with ID " + id + " does not exist!");
      }
    }

    /**
     * Tallentaa parametreina annetun kirjasarjan tietokantaan.
     * 
     * @param sarja tallennettava kirjasarja
     * @return Tallennettu kirjasarja.
     */
    public KirjaSarja saveKirjaSarja(KirjaSarja sarja) {
      try {
        return kirjaSarjaRepository.save(sarja);
      } catch (IllegalArgumentException e) {
        throw new NullKirjaSarjaException("Tietokantaan menevä olio tyhjä. Tarkista parametrit!");
      }
    }

    /**
     * Poistaa kirjasarjan tietokannasta annetun ID-numeron perusteella. Ei palauta mitään.
     * 
     * @param id Poistettavan kirjasarjan ID kokonaislukuna.
     */
    public void removeKirjaSarja(@NonNull Long id) {
      try {
        kirjaSarjaRepository.deleteById(id);
      } catch (IllegalArgumentException e) {
        throw new NonExistingKirjaSarjaException("Kirjasarja with ID " + id + " does not exist!");
      } 
    }
}