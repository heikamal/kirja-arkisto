package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaKopioException;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.repositories.KirjaKopioRepository;

import java.util.List;

/**
 * Palveluluokka kirjakopioiden hallintaa varten.
 */
@Service
public class KirjaKopioService {

  /**
   * Repository, jolla voidaan hallita kirjakopioita tietokannassa.
   */
    @Autowired
    private KirjaKopioRepository KirjakopioRepository;

    /**
     * Palauttaa kaikki tietokannasta löytyvät kirjakopiot.
     * 
     * @return Lista kirjakopioista.
     */
    public List<KirjaKopio> getKirjakopiot() {
        return KirjakopioRepository.findAll();
    }

    /**
     * Palauttaa kirjakopion, joka vastaa annettua ID:ta.
     * 
     * @param id Kirjakopion ID
     * @return Tietokannasta haettu kirjakopio.
     */
    public KirjaKopio getKirjakopioById(Long id) {
        KirjaKopio kopio = KirjakopioRepository.findById(id).orElse(null);
        if (kopio == null) {
            throw new NonExistingKirjaKopioException("Kirja kopio with ID " + id + " does not exist!");
        }
        return kopio;
    }

    /**
     * Tallentaa kirjakopion tietokantaan. Käyttää repositoryn save-metodia, joka ylikirjoittaa olemassa olevan kopion.
     *
     * @param kirjaKopio tallennettavat kirjakopio.
     * @return Tallennettu kirjakopio.
     */
    public KirjaKopio saveKirjaKopio(KirjaKopio kirjaKopio){
        return KirjakopioRepository.save(kirjaKopio);
    }

    // Lisää tarvittavat liiketoimintalogiikkametodit
}