package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.repositories.KirjaHyllyRepository;

import java.util.List;

/**
 * Kirjahyllyjen hallintaan tehty palveluluokka.
 */
@Service
public class KirjaHyllyService {

  /**
   * Repository hallitsemaan kirjahyllyjä tietokannassa.
   */
    @Autowired
    private KirjaHyllyRepository KirjahyllyRepository;

    /**
     * Metodi hakee kaikki kirjahyllyt.
     * 
     * @return Kaikki tietokannassa olevat kirjahyllyt listana.
     */
    public List<KirjaHylly> getKirjahyllyt() {
        return KirjahyllyRepository.findAll();
    }

    /**
     * Metodi hakee kirjahyllyn id:n perusteella.
     * 
     * @param id Kirjahyllyn id.
     * @return Kirjahyllyn id:n perusteella oleva kirjahylly.
     */
    public KirjaHylly getKirjahyllyById(Long id) {
        return KirjahyllyRepository.findById(id).orElse(null);
    }

    /**
     * Metodi hakee kirjahyllyn omistajan id:n perusteella.
     * 
     * @param omistaja Kirjahyllyn omistajan id.
     * @return Kirjahyllyn omistajan id:n perusteella oleva kirjahylly.
     */
    public KirjaHylly getKirjaHyllyByOmistaja(Long omistaja) {
        return KirjahyllyRepository.findByOmistaja(omistaja);
    }

    /**
     * Metodi tallentaa uuden kirjahyllyn tietokantaan. Käyttää repositoryn save-metodia, joten jos kirjahylly 
     * löytyy jo tietokannasta, niin metodi kirjoittaa olemassa olevan kirjahyllyn päälle.
     * 
     * @param kirjahylly Uusi kirjahylly.
     * @return Tallennettu kirjahylly.
     */
    public KirjaHylly saveKirjaHylly(KirjaHylly kirjahylly) {
        return KirjahyllyRepository.save(kirjahylly);
    }

    // Lisää tarvittavat liiketoimintalogiikkametodit
}